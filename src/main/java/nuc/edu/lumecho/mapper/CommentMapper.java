package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (post_id, user_id, content, parent_id, create_time) " +
            "VALUES (#{postId}, #{userId}, #{content}, #{parentId}, #{createTime})")
    void insert(Comment comment);

    @Select("SELECT * FROM comment WHERE post_id = #{postId} ORDER BY create_time ASC")
    List<Comment> findAllByPostId(Long postId);
}
