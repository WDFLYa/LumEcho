package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.dto.response.user.UserBaseInfoResponse;
import nuc.edu.lumecho.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(1) FROM user WHERE account = #{account} AND deleted_at IS NULL")
    boolean existsByAccount(@Param("account") String account);

    @Select("SELECT COUNT(1) FROM user WHERE phone = #{phone} AND deleted_at IS NULL")
    boolean existsByPhone(@Param("phone") String phone);

    @Insert({
            "INSERT INTO user (account, phone, password, status, create_time)",
            "VALUES (#{account}, #{phone}, #{password}, #{status}, #{createTime})"
    })
    void insert(User user);

    @Select("SELECT password FROM user WHERE account = #{account} AND deleted_at IS NULL")
    String selectPasswordByAccount(@Param("account") String account);

    @Select("SELECT id FROM user WHERE account = #{account} AND deleted_at IS NULL")
    Long selectUserIdByAccount(@Param("account") String account);

    @Select("SELECT id FROM user WHERE phone = #{phone} AND deleted_at IS NULL")
    Long selectUserIdByPhone(@Param("phone") String phone);

    void updateUser(User user);

    UserBaseInfoResponse selectUserBaseInfoById(@Param("id") Long id);

    List<UserBaseInfoResponse> selectUserBaseInfoByIds(@Param("ids") List<Long> ids);

    @Select("SELECT role FROM user WHERE id = #{id} AND deleted_at IS NULL")
    String getUserRoleById(@Param("id") Long id);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserById(@Param("id") Long id);
}
