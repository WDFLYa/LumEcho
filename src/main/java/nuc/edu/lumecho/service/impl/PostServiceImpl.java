package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.common.Enum.PostStatusEnum;
import nuc.edu.lumecho.common.Enum.PostTypeEnum;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.mapper.PostMapper;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostRequest;
import nuc.edu.lumecho.model.entity.Post;
import nuc.edu.lumecho.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

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
        post.setPostStatus(PostStatusEnum.NORMAL.getValue());
        post.setCreateTime(LocalDateTime.now());
        post.setUpdateTime(LocalDateTime.now());
        postMapper.insert(post);
    }

    @Override
    public void updatePost(UpdatePostRequest updatePostRequest) {
        Post post = postMapper.selectByIdAndUserId(updatePostRequest.getId(), WdfUserContext.getCurrentUserId());
        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());
        post.setUpdateTime(LocalDateTime.now());
        postMapper.updateById(post);
    }
}
