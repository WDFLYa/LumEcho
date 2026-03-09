package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostMapper {
    void insert(Post post);

    void updateById(Post post);

    @Select("SELECT * FROM posts WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    Post selectByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

}
