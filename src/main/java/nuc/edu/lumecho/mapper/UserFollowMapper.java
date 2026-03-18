package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.UserFollow;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFollowMapper {


    @Insert("INSERT INTO user_follow (follower_id, following_id, create_time, update_time) " +
            "VALUES (#{followerId}, #{followingId}, NOW(), NOW())")
    int insertFollow(@Param("followerId") Long followerId, @Param("followingId") Long followingId);


    @Delete("DELETE FROM user_follow WHERE follower_id = #{followerId} AND following_id = #{followingId}")
    int deleteFollow(@Param("followerId") Long followerId, @Param("followingId") Long followingId);


    @Select("SELECT COUNT(*) FROM user_follow WHERE follower_id = #{followerId} AND following_id = #{followingId}")
    int checkFollowStatus(@Param("followerId") Long followerId, @Param("followingId") Long followingId);


    @Select("SELECT COUNT(*) FROM user_follow WHERE following_id = #{userId}")
    long countFans(@Param("userId") Long userId);


    @Select("SELECT COUNT(*) FROM user_follow WHERE follower_id = #{userId}")
    long countFollows(@Param("userId") Long userId);


    @Update("UPDATE user SET fans_count = fans_count + #{delta} WHERE id = #{userId}")
    int updateFansCount(@Param("userId") Long userId, @Param("delta") int delta);

    @Update("UPDATE user SET follow_count = follow_count + #{delta} WHERE id = #{userId}")
    int updateFollowCount(@Param("userId") Long userId, @Param("delta") int delta);

    @Select("SELECT * FROM user_follow WHERE follower_id = #{userId}")
    List<UserFollow> selectFollowList(@Param("userId") Long userId);

    @Select("SELECT * FROM user_follow WHERE following_id = #{userId}")
    List<UserFollow> selectFansList(@Param("userId") Long userId);
}

