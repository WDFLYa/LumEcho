package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PostMapper {
    void insert(Post post);

    void updatePost(Post post);

    @Select("SELECT * FROM posts WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    Post selectByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Update("UPDATE posts SET post_status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
