package nuc.edu.lumecho.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostLikeMapper {


    @Select("SELECT COUNT(1) > 0 FROM post_like WHERE user_id = #{userId} AND post_id = #{postId}")
    boolean existsByUserAndPost(@Param("userId") Long userId, @Param("postId") Long postId);

    @Insert("INSERT INTO post_like (user_id, post_id) VALUES (#{userId}, #{postId})")
    void insert(@Param("userId") Long userId, @Param("postId") Long postId);

    @Delete("DELETE FROM post_like WHERE user_id = #{userId} AND post_id = #{postId}")
    void deleteByUserAndPost(@Param("userId") Long userId, @Param("postId") Long postId);

    @Select({
            "<script>",
            "SELECT post_id FROM post_like",
            "WHERE user_id = #{userId}",
            "AND post_id IN",
            "<foreach collection='postIds' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Long> selectLikedPostIdsByUser(@Param("userId") Long userId,
                                        @Param("postIds") List<Long> postIds);
}
