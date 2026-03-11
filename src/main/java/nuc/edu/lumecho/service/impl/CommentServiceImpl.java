package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.CommentMapper;
import nuc.edu.lumecho.mapper.PostMapper;
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

    @Autowired
    private PostMapper postMapper;

    @Override
    public void createComment(CreateCommentRequest createCommentRequest) {
        Comment comment = new Comment();
        comment.setPostId(createCommentRequest.getPostId());
        comment.setContent(createCommentRequest.getContent());
        comment.setParentId(createCommentRequest.getParentId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setUserId(WdfUserContext.getCurrentUserId());
        commentMapper.insert(comment);
        postMapper.incrementCommentCount(comment.getPostId());
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

    // ====== 分页查询评论树（只对一级评论分页） ======

    @Override
    public Map<String, Object> getCommentTreeWithPagination(Long postId, int page, int size) {
        if (page < 1) page = 1;
        if (size < 1 || size > 50) size = 10;

        int offset = (page - 1) * size;

        // 1. 查询一级评论（分页）
        List<Comment> topLevelComments = commentMapper.selectTopLevelComments(postId, offset, size);
        int total = commentMapper.countTopLevelComments(postId);

        List<CommentNode> result = new ArrayList<>();
        if (!topLevelComments.isEmpty()) {
            // 2. 查询该帖子的所有子评论（不分页）
            List<Comment> allChildComments = commentMapper.selectAllChildComments(postId);

            // 3. 合并所有评论（一级 + 子级）
            List<Comment> allComments = new ArrayList<>(topLevelComments);
            allComments.addAll(allChildComments);

            // 4. 构建 commentMap 和 userMap（复用你原有的逻辑）
            Map<Long, Comment> commentMap = allComments.stream()
                    .collect(Collectors.toMap(Comment::getId, c -> c));

            Set<Long> userIds = allComments.stream().map(Comment::getUserId).collect(Collectors.toSet());
            List<UserBaseInfoResponse> users = userMapper.selectUserBaseInfoByIds(new ArrayList<>(userIds));
            Map<Long, UserBaseInfoResponse> userMap = users.stream()
                    .collect(Collectors.toMap(UserBaseInfoResponse::getId, u -> u));

            // 5. 构建 nodeMap
            Map<Long, CommentNode> nodeMap = new HashMap<>();
            for (Comment comment : allComments) {
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

            // 6. 构建树（只将一级评论加入 roots）
            List<CommentNode> roots = new ArrayList<>();
            for (Comment top : topLevelComments) {
                CommentNode node = nodeMap.get(top.getId());
                // 找到它的所有后代（递归已在 nodeMap 中）
                attachChildren(node, nodeMap, commentMap);
                roots.add(node);
            }

            // 7. 排序：一级倒序，子级升序（复用你原有逻辑）
            roots.sort((a, b) -> {
                LocalDateTime timeA = commentMap.get(a.getId()).getCreateTime();
                LocalDateTime timeB = commentMap.get(b.getId()).getCreateTime();
                return timeB.compareTo(timeA);
            });

            for (CommentNode root : roots) {
                sortChildrenAsc(root, commentMap);
            }

            result = roots;
        }

        // 8. 返回分页结果
        Map<String, Object> response = new HashMap<>();
        response.put("list", result);
        response.put("total", total);
        response.put("totalPages", (int) Math.ceil((double) total / size));
        response.put("currentPage", page);
        response.put("pageSize", size);
        return response;
    }

    // 辅助方法：递归挂载子评论（避免循环引用）
    private void attachChildren(CommentNode parent, Map<Long, CommentNode> nodeMap, Map<Long, Comment> commentMap) {
        List<Comment> children = commentMap.values().stream()
                .filter(c -> Objects.equals(c.getParentId(), parent.getId()))
                .sorted(Comparator.comparing(Comment::getCreateTime)) // 子评论按时间升序
                .toList();

        for (Comment child : children) {
            CommentNode childNode = nodeMap.get(child.getId());
            if (childNode != null) {
                attachChildren(childNode, nodeMap, commentMap); // 递归
                parent.getChildren().add(childNode);
            }
        }
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
