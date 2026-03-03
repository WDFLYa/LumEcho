package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(1) FROM user WHERE account = #{account}")
    boolean existsByAccount(@Param("account") String account);

    @Select("SELECT COUNT(1) FROM user WHERE phone = #{phone}")
    boolean existsByPhone(@Param("phone") String phone);

    @Insert({
            "INSERT INTO user (account, phone, password, status, create_time)",
            "VALUES (#{account}, #{phone}, #{password}, #{status}, #{createTime})"
    })
    void insert(User user);

    @Select("SELECT password FROM user WHERE account = #{account}")
    String selectPasswordByAccount(@Param("account") String account);

    @Select("SELECT id FROM user WHERE account = #{account}")
    Long selectUserIdByAccount(@Param("account") String account);

    @Select("SELECT id FROM user WHERE phone = #{phone}")
    Long selectUserIdByPhone(@Param("phone") String phone);

    void updateUser(User user);
}
