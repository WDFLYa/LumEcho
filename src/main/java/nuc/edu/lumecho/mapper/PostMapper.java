package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.dto.response.post.PostHomeItemResponse;
import nuc.edu.lumecho.model.dto.response.post.PostProfileResponse;
import nuc.edu.lumecho.model.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PostMapper {
    void insert(Post post);

    void updatePost(Post post);

    @Select("SELECT * FROM posts WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    Post selectByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Update("UPDATE posts SET post_status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Select("SELECT * FROM posts WHERE id = #{id} AND deleted_at IS NULL")
    Post selectById(@Param("id") Long id);


    List<PostHomeItemResponse> selectHomePosts(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    List<PostHomeItemResponse> selectHomePostsByHot(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );



    // 查询总数
    long countUserPosts(@Param("userId") Long userId);
    long countValidPosts(@Param("userId") Long userId, @Param("categoryId") Long categoryId);


    @Update("UPDATE posts SET comment_count = comment_count + 1 WHERE id = #{postId}")
    void incrementCommentCount(@Param("postId") Long postId);

    @Update("UPDATE posts SET like_count = like_count + 1 WHERE id = #{postId}")
    void incrementLikeCount(@Param("postId") Long postId);

    @Update("UPDATE posts SET like_count = like_count - 1 WHERE id = #{postId} AND like_count > 0")
    void decrementLikeCount(@Param("postId") Long postId);

}
