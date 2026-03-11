package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM category WHERE status = 1")
    List<Category> getAllCategories();

    @Insert("INSERT INTO category (name, status) VALUES (#{name}, #{status})")
    void insertCategory(Category category);

    void updateCategory(Category category);
}
