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

    @Insert({
            "INSERT INTO user (account, password, status, create_time)",
            "VALUES (#{account}, #{password}, #{status}, #{createTime})"
    })
    void insert(User user);
}
