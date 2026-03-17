package nuc.edu.lumecho.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper {

    @Insert("INSERT INTO user_follow (follower_id, following_id, create_time, update_time) " +
            "VALUES (#{followerId}, #{followingId}, NOW(), NOW())")
    int insertFollow(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    @Delete("DELETE FROM user_follow WHERE follower_id = #{followerId} AND following_id = #{followingId}")
    int deleteFollow(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    @Select("SELECT COUNT(*) FROM user_follow WHERE follower_id = #{followerId} AND following_id = #{followingId}")
    int checkFollowStatus(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

}
}
