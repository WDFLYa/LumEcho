package nuc.edu.lumecho.service;
import nuc.edu.lumecho.model.dto.request.post.PostIdRequest;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostRequest;
import nuc.edu.lumecho.model.dto.response.post.PostDetailResponse;
import nuc.edu.lumecho.model.dto.response.post.PostHomeItemResponse;
import nuc.edu.lumecho.model.dto.response.post.PostHomePageResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PostService {
    void publishPost(PublishPostRequest publishPostRequest);
    void updatePost(UpdatePostRequest updatePostRequest);
    void deletePost(PostIdRequest postIdRequest);
    PostDetailResponse selectPostById(Long id);
    PostHomePageResponse selectHomePosts(
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    PostHomePageResponse selectHomePostsByHot(
            @Param("offset") int offset,
            @Param("limit") int limit
    );
}
