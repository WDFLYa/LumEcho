package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Address;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {
    @Insert("INSERT INTO address (user_id, province, city, district, detail) " +
            "VALUES (#{userId}, #{province}, #{city}, #{district}, #{detail})")
    void insert(Address address);

    @Insert("SELECT * FROM address WHERE user_id = #{userId}")
    Address selectByUserId(Long userId);

    @Select("SELECT * FROM address WHERE user_id = #{userId}")
    List<Address> listByUserId(Long userId);

    @Delete("DELETE FROM address WHERE id = #{id}")
    void delete(Long id);

}
