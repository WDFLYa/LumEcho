package nuc.edu.lumecho.service;
import nuc.edu.lumecho.model.dto.request.post.PostIdRequest;
import nuc.edu.lumecho.model.dto.request.post.PublishPostRequest;
import nuc.edu.lumecho.model.dto.request.post.UpdatePostRequest;
import nuc.edu.lumecho.model.dto.response.post.PostDetailResponse;
import nuc.edu.lumecho.model.dto.response.post.PostHomeItemResponse;
import nuc.edu.lumecho.model.dto.response.post.PostHomePageResponse;
import nuc.edu.lumecho.model.dto.response.post.PostProfileResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PostService {
    void publishPost(PublishPostRequest publishPostRequest);
    void updatePost(UpdatePostRequest updatePostRequest);
    void deletePost(PostIdRequest postIdRequest);
    PostDetailResponse selectPostById(Long id);

    PostHomePageResponse selectAllPosts(
            String keyword,
            Integer status,
            int offset,
            int limit);
    PostHomePageResponse selectHomePosts(
            @Param("offset") int offset,
            @Param("limit") int limit
    );



    PostHomePageResponse selectHomePostsByHot(
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    PostHomePageResponse selectHomePostsByCategory(
            @Param("categoryId") Long categoryId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    PostHomePageResponse selectHomePostsByCategoryAndHot(
            @Param("categoryId") Long categoryId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    PostHomePageResponse selectPostsByUserId(
            @Param("userId") Long userId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );


    public PostHomePageResponse selectPostsByUserLike(Long userId, int offset, int limit);



    PostHomePageResponse searchPostsByHot(String keyword, Long categoryId, int offset, int limit);

    PostHomePageResponse searchPostsByTime(String keyword, Long categoryId, int offset, int limit);
}
