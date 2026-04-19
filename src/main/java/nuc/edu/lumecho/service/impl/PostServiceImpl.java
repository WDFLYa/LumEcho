package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.PostStatusEnum;
import nuc.edu.lumecho.common.Enum.PostTypeEnum;
import nuc.edu.lumecho.common.Enum.ResourceTypeEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.CategoryMapper;
import nuc.edu.lumecho.mapper.PostMapper;

import nuc.edu.lumecho.mapper.ResourceFileMapper;
import nuc.edu.lumecho.mapper.UserMapper;
import nuc.edu.lumecho.model.dto.request.post.PostIdRequest;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostRequest;

import nuc.edu.lumecho.model.dto.response.post.PostDetailResponse;
import nuc.edu.lumecho.model.dto.response.post.PostHomeItemResponse;
import nuc.edu.lumecho.model.dto.response.post.PostHomePageResponse;
import nuc.edu.lumecho.model.dto.response.post.PostProfileResponse;
import nuc.edu.lumecho.model.dto.response.user.UserBaseInfoResponse;
import nuc.edu.lumecho.model.entity.Post;
import nuc.edu.lumecho.model.entity.ResourceFile;
import nuc.edu.lumecho.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ResourceFileMapper resourceFileMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;



    @Override
    public void publishPost(PublishPostRequest publishPostRequest) {
        Post post = new Post();
        post.setPostType(PostTypeEnum.GENERAL.getCode());
        post.setCategoryId(publishPostRequest.getCategoryId());
        post.setUserId(WdfUserContext.getCurrentUserId());
        post.setTitle(publishPostRequest.getTitle());
        post.setContent(publishPostRequest.getContent());
        post.setViewCount(0L);
        post.setLikeCount(0L);
        post.setCommentCount(0L);
        post.setPinned(false);
        post.setPostStatus(PostStatusEnum.NORMAL.getCode());
        post.setCreateTime(LocalDateTime.now());
        post.setUpdateTime(LocalDateTime.now());
        postMapper.insert(post);
        ResourceFile resourceFile = new ResourceFile();
        resourceFile.setBizType(ResourceTypeEnum.POST_COVER.getCode());
        resourceFile.setBizId(post.getId());
        resourceFile.setFileUrl(publishPostRequest.getImageUrls().get(0));
        resourceFile.setFileName(post.getId() + "Cover");
        resourceFile.setCreatedBy(WdfUserContext.getCurrentUserId());
        resourceFile.setCreatedTime(LocalDateTime.now());
        resourceFileMapper.insert(resourceFile);
        resourceFileMapper.bindBizIdBatch(publishPostRequest.getImageUrls(),ResourceTypeEnum.POST_IMAGE.getCode(),post.getId());

    }

    @Override
    public void updatePost(UpdatePostRequest updatePostRequest) {
        Post post = postMapper.selectByIdAndUserId(updatePostRequest.getId(), WdfUserContext.getCurrentUserId());
        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());
        post.setUpdateTime(LocalDateTime.now());
        postMapper.updatePost(post);
    }

    @Override
    public void deletePost(PostIdRequest postIdRequest) {
        Post post = postMapper.selectByIdAndUserId(postIdRequest.getId(), WdfUserContext.getCurrentUserId());
        post.setDeletedAt(LocalDateTime.now());
        postMapper.updatePost(post);
    }

    @Override
    public PostDetailResponse selectPostById(Long id) {
        Post post = postMapper.selectById(id);
        PostDetailResponse postDetailResponse = new PostDetailResponse();
        postDetailResponse.setId(post.getId());
        postDetailResponse.setTitle(post.getTitle());
        postDetailResponse.setContent(post.getContent());
        postDetailResponse.setLikeCount(post.getLikeCount());
        postDetailResponse.setCommentCount(post.getCommentCount());
        postDetailResponse.setUserId(post.getUserId());
        postDetailResponse.setCreateTime(post.getCreateTime());
        List<ResourceFile> resourceFiles = resourceFileMapper.selectByBizIdAndTypes(post.getId(), List.of(
                ResourceTypeEnum.POST_IMAGE.getCode(),
                ResourceTypeEnum.POST_VIDEO.getCode()
        ));
        List<String> imageUrls = resourceFiles.stream()
                .filter(f -> ResourceTypeEnum.POST_IMAGE.getCode().equals(f.getBizType()))
                .map(ResourceFile::getFileUrl)
                .toList();

        List<String> videoUrls = resourceFiles.stream()
                .filter(f -> ResourceTypeEnum.POST_VIDEO.getCode().equals(f.getBizType()))
                .map(ResourceFile::getFileUrl)
                .toList();

        postDetailResponse.setImageUrls(imageUrls);
        postDetailResponse.setVideoUrls(videoUrls);
        UserBaseInfoResponse userBaseInfoResponse = userMapper.selectUserBaseInfoById(post.getUserId());
        postDetailResponse.setUsername(userBaseInfoResponse.getUsername());
        postDetailResponse.setAvatar(userBaseInfoResponse.getAvatar());
        postDetailResponse.setCategoryName(categoryMapper.selectCategoryNameById(post.getCategoryId()));

        return postDetailResponse;
    }

    @Override
    public PostHomePageResponse selectAllPosts(String keyword, Integer status, int offset, int limit) {

        List<PostHomeItemResponse> list =
                postMapper.selectAllPosts(keyword, status, null, null, offset, limit);

        for (PostHomeItemResponse item : list) {
            if (item.getCreateTime() != null) {
                item.setTimeAgo(formatTimeAgo(item.getCreateTime()));
            }
        }

        long total = postMapper.countValidAllPosts(keyword, status, null, null);

        PostHomePageResponse res = new PostHomePageResponse();
        res.setData(list);
        res.setHasMore((offset + list.size()) < total);
        res.setTotal(total);

        return res;
    }
    @Override
    public PostHomePageResponse selectHomePosts(int offset, int limit) {
        List<PostHomeItemResponse> postHomeItemResponses = postMapper.selectHomePosts(null,null,null,offset, limit);
        for (PostHomeItemResponse item : postHomeItemResponses) {
            LocalDateTime createdTime = item.getCreateTime();
            if (createdTime != null) {
                item.setTimeAgo(formatTimeAgo(createdTime));
            } else {
                item.setTimeAgo("未知时间");
            }
        }

        long total = postMapper.countValidPosts(null,null, null);

        boolean hasMore = (offset + postHomeItemResponses.size()) < total;

        PostHomePageResponse postHomePageResponse = new PostHomePageResponse();
        postHomePageResponse.setData(postHomeItemResponses);
        postHomePageResponse.setHasMore(hasMore);
        postHomePageResponse.setTotal(total);

        return postHomePageResponse;
    }

    @Override
    public PostHomePageResponse selectHomePostsByHot(int offset, int limit) {
        List<PostHomeItemResponse> postHomeItemResponses = postMapper.selectHomePostsByHot(null,null,null,offset, limit);
        for (PostHomeItemResponse item : postHomeItemResponses) {
            LocalDateTime createdTime = item.getCreateTime();
            if (createdTime != null) {
                item.setTimeAgo(formatTimeAgo(createdTime));
            } else {
                item.setTimeAgo("未知时间");
            }
        }

        long total = postMapper.countValidPosts(null,null, null);

        boolean hasMore = (offset + postHomeItemResponses.size()) < total;

        PostHomePageResponse postHomePageResponse = new PostHomePageResponse();
        postHomePageResponse.setData(postHomeItemResponses);
        postHomePageResponse.setHasMore(hasMore);
        postHomePageResponse.setTotal(total);

        return postHomePageResponse;
    }

    @Override
    public PostHomePageResponse selectHomePostsByCategory(Long categoryId, int offset, int limit) {
        List<PostHomeItemResponse> postHomeItemResponses = postMapper.selectHomePosts(null,categoryId,null,offset, limit);
        for (PostHomeItemResponse item : postHomeItemResponses) {
            LocalDateTime createdTime = item.getCreateTime();
            if (createdTime != null) {
                item.setTimeAgo(formatTimeAgo(createdTime));
            } else {
                item.setTimeAgo("未知时间");
            }
        }

        long total = postMapper.countValidPosts(null,null, categoryId);

        boolean hasMore = (offset + postHomeItemResponses.size()) < total;

        PostHomePageResponse postHomePageResponse = new PostHomePageResponse();
        postHomePageResponse.setData(postHomeItemResponses);
        postHomePageResponse.setHasMore(hasMore);
        postHomePageResponse.setTotal(total);

        return postHomePageResponse;
    }

    @Override
    public PostHomePageResponse selectHomePostsByCategoryAndHot(Long categoryId, int offset, int limit) {
        List<PostHomeItemResponse> postHomeItemResponses = postMapper.selectHomePostsByHot(null,categoryId,null,offset, limit);
        for (PostHomeItemResponse item : postHomeItemResponses) {
            LocalDateTime createdTime = item.getCreateTime();
            if (createdTime != null) {
                item.setTimeAgo(formatTimeAgo(createdTime));
            } else {
                item.setTimeAgo("未知时间");
            }
        }

        long total = postMapper.countValidPosts(null,null, categoryId);

        boolean hasMore = (offset + postHomeItemResponses.size()) < total;

        PostHomePageResponse postHomePageResponse = new PostHomePageResponse();
        postHomePageResponse.setData(postHomeItemResponses);
        postHomePageResponse.setHasMore(hasMore);
        postHomePageResponse.setTotal(total);

        return postHomePageResponse;
    }

    @Override
    public PostHomePageResponse selectPostsByUserId(Long userId, int offset, int limit) {
        List<PostHomeItemResponse> postHomeItemResponses = postMapper.selectHomePosts(null,null,userId,offset, limit);
        for (PostHomeItemResponse item : postHomeItemResponses) {
            LocalDateTime createdTime = item.getCreateTime();
            if (createdTime != null) {
                item.setTimeAgo(formatTimeAgo(createdTime));
            } else {
                item.setTimeAgo("未知时间");
            }
        }

        long total = postMapper.countValidPosts(null,userId, null);

        boolean hasMore = (offset + postHomeItemResponses.size()) < total;

        PostHomePageResponse postHomePageResponse = new PostHomePageResponse();
        postHomePageResponse.setData(postHomeItemResponses);
        postHomePageResponse.setHasMore(hasMore);
        postHomePageResponse.setTotal(total);

        return postHomePageResponse;
    }

    // PostServiceImpl.java
    @Override
    public PostHomePageResponse selectPostsByUserLike(Long userId, int offset, int limit) {
        // 1. 查询用户点赞的帖子列表（按点赞时间排序）
        List<PostHomeItemResponse> postHomeItemResponses = postMapper.selectPostsByUserLike(userId, offset, limit);

        // 2. 处理 timeAgo 字段
        for (PostHomeItemResponse item : postHomeItemResponses) {
            LocalDateTime createdTime = item.getCreateTime();
            if (createdTime != null) {
                item.setTimeAgo(formatTimeAgo(createdTime));
            } else {
                item.setTimeAgo("未知时间");
            }
        }

        // 3. 查询总数
        long total = postMapper.countLikedPosts(userId);

        // 4. 判断是否有更多
        boolean hasMore = (offset + postHomeItemResponses.size()) < total;

        // 5. 组装响应
        PostHomePageResponse postHomePageResponse = new PostHomePageResponse();
        postHomePageResponse.setData(postHomeItemResponses);
        postHomePageResponse.setHasMore(hasMore);
        postHomePageResponse.setTotal(total);

        return postHomePageResponse;
    }


    @Override
    public PostHomePageResponse searchPostsByHot(String keyword, Long categoryId, int offset, int limit) {
        List<PostHomeItemResponse> postHomeItemResponses = postMapper.selectHomePostsByHot(keyword,categoryId,null,offset, limit);
        for (PostHomeItemResponse item : postHomeItemResponses) {
            LocalDateTime createdTime = item.getCreateTime();
            if (createdTime != null) {
                item.setTimeAgo(formatTimeAgo(createdTime));
            } else {
                item.setTimeAgo("未知时间");
            }
        }

        long total = postMapper.countValidPosts(keyword,null, categoryId);

        boolean hasMore = (offset + postHomeItemResponses.size()) < total;

        PostHomePageResponse postHomePageResponse = new PostHomePageResponse();
        postHomePageResponse.setData(postHomeItemResponses);
        postHomePageResponse.setHasMore(hasMore);
        postHomePageResponse.setTotal(total);

        return postHomePageResponse;
    }

    @Override
    public PostHomePageResponse searchPostsByTime(String keyword, Long categoryId, int offset, int limit) {
        List<PostHomeItemResponse> postHomeItemResponses = postMapper.selectHomePosts(keyword,categoryId,null,offset, limit);
        for (PostHomeItemResponse item : postHomeItemResponses) {
            LocalDateTime createdTime = item.getCreateTime();
            if (createdTime != null) {
                item.setTimeAgo(formatTimeAgo(createdTime));
            } else {
                item.setTimeAgo("未知时间");
            }
        }

        long total = postMapper.countValidPosts(keyword,null, categoryId);

        boolean hasMore = (offset + postHomeItemResponses.size()) < total;

        PostHomePageResponse postHomePageResponse = new PostHomePageResponse();
        postHomePageResponse.setData(postHomeItemResponses);
        postHomePageResponse.setHasMore(hasMore);
        postHomePageResponse.setTotal(total);

        return postHomePageResponse;
    }


    private String formatTimeAgo(LocalDateTime postTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(postTime, now);

        long seconds = Math.abs(duration.getSeconds());

        if (seconds < 60) {
            return "刚刚";
        } else if (seconds < 3600) { // 小于1小时
            return (seconds / 60) + "分钟前";
        } else if (seconds < 86400) { // 小于1天
            return (seconds / 3600) + "小时前";
        } else if (seconds < 2592000) { // 小于30天
            return (seconds / 86400) + "天前";
        } else {
            // 超过30天，显示日期（如：2025-03-10）
            return postTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}