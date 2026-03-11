package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.CommentMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.comment.CreateCommentRequest;
import nuc.edu.lumecho.model.dto.response.user.UserBaseInfoResponse;
import nuc.edu.lumecho.model.entity.Comment;
import nuc.edu.lumecho.model.entity.User;
import nuc.edu.lumecho.model.vo.CommentNode;
import nuc.edu.lumecho.service.CommentService;
import nuc.edu.lumecho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createComment(CreateCommentRequest createCommentRequest) {
        Comment comment = new Comment();
        comment.setPostId(createCommentRequest.getPostId());
        comment.setContent(createCommentRequest.getContent());
        comment.setParentId(createCommentRequest.getParentId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setUserId(WdfUserContext.getCurrentUserId());
        commentMapper.insert(comment);
    }

    @Override
    public List<CommentNode> getCommentTree(Long postId) {
        List<Comment> comments = commentMapper.findAllByPostId(postId);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }

        // 构建 commentMap
        Map<Long, Comment> commentMap = comments.stream()
                .collect(Collectors.toMap(Comment::getId, c -> c));

        // 获取所有 userId
        Set<Long> userIds = comments.stream().map(Comment::getUserId).collect(Collectors.toSet());

        List<Long> userIdList = new ArrayList<>(userIds);
        List<UserBaseInfoResponse> users = userMapper.selectUserBaseInfoByIds(userIdList);

        // 构建 userMap
        Map<Long, UserBaseInfoResponse> userMap = users.stream()
                .collect(Collectors.toMap(UserBaseInfoResponse::getId, u -> u));

        // 构建节点 map
        Map<Long, CommentNode> nodeMap = new HashMap<>();
        for (Comment comment : comments) {
            CommentNode node = new CommentNode();
            node.setId(comment.getId());
            node.setUserId(comment.getUserId());
            node.setContent(comment.getContent());
            node.setCreateTimeAgo(formatTimeAgo(comment.getCreateTime()));

            UserBaseInfoResponse user = userMap.get(comment.getUserId());
            if (user != null) {
                node.setUsername(user.getUsername());
                node.setAvatar(user.getAvatar());
            } else {
                node.setUsername("已注销");
                node.setAvatar("/default-avatar.png");
            }

            nodeMap.put(comment.getId(), node);
        }

        // 构建树
        List<CommentNode> roots = new ArrayList<>();
        for (Comment comment : comments) {
            CommentNode node = nodeMap.get(comment.getId());
            if (comment.getParentId() == null) {
                roots.add(node);
            } else {
                CommentNode parent = nodeMap.get(comment.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }

        // 一级评论按时间倒序（最新在上）
        roots.sort((a, b) -> {
            LocalDateTime timeA = commentMap.get(a.getId()).getCreateTime();
            LocalDateTime timeB = commentMap.get(b.getId()).getCreateTime();
            return timeB.compareTo(timeA);
        });

        // 子评论按时间升序（先说的在上）
        for (CommentNode root : roots) {
            sortChildrenAsc(root, commentMap);
        }

        return roots;
    }

    // 递归排序子评论（升序）
    private void sortChildrenAsc(CommentNode node, Map<Long, Comment> commentMap) {
        node.getChildren().sort((a, b) -> {
            LocalDateTime timeA = commentMap.get(a.getId()).getCreateTime();
            LocalDateTime timeB = commentMap.get(b.getId()).getCreateTime();
            return timeA.compareTo(timeB);
        });
        for (CommentNode child : node.getChildren()) {
            sortChildrenAsc(child, commentMap);
        }
    }

    // 时间友好格式
    public static String formatTimeAgo(LocalDateTime pastTime) {
        if (pastTime == null) return "未知时间";
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(pastTime, now);
        long seconds = Math.abs(duration.getSeconds());

        if (seconds < 60) return "刚刚";
        if (seconds < 3600) return (seconds / 60) + "分钟前";
        if (seconds < 86400) return (seconds / 3600) + "小时前";
        if (seconds < 2592000) return (seconds / 86400) + "天前";
        if (seconds < 31536000) return (seconds / 2592000) + "个月前";
        return (seconds / 31536000) + "年前";
    }
}
