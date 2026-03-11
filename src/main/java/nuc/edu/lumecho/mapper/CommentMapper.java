package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (post_id, user_id, content, parent_id, create_time) " +
            "VALUES (#{postId}, #{userId}, #{content}, #{parentId}, #{createTime})")
    void insert(Comment comment);

    @Select("SELECT * FROM comment WHERE post_id = #{postId} ORDER BY create_time ASC")
    List<Comment> findAllByPostId(Long postId);

    /**
     * 查询某帖子的一级评论（分页）
     */
    @Select({
            "<script>",
            "SELECT id, user_id, content, parent_id, create_time",
            "FROM comment",
            "WHERE post_id = #{postId}",
            "  AND (parent_id IS NULL)",
            "ORDER BY create_time DESC",
            "LIMIT #{offset}, #{limit}",
            "</script>"
    })
    List<Comment> selectTopLevelComments(@Param("postId") Long postId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    /**
     * 查询某帖子的所有子评论（用于构建树）
     */
    @Select("SELECT id, user_id, content, parent_id, create_time " +
            "FROM comment " +
            "WHERE post_id = #{postId} " +
            "  AND parent_id IS NOT NULL " +
            "ORDER BY create_time ASC")
    List<Comment> selectAllChildComments(@Param("postId") Long postId);

    /**
     * 获取一级评论总数
     */
    @Select("SELECT COUNT(1) FROM comment " +
            "WHERE post_id = #{postId} " +
            "  AND (parent_id IS NULL) ")
    int countTopLevelComments(@Param("postId") Long postId);
}
