package nuc.edu.lumecho.mapper;

import nuc.edu.lumecho.model.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM categories WHERE status = 1")
    List<Category> getAllCategories();

    @Insert("INSERT INTO categories (name, status) VALUES (#{name}, #{status})")
    void insertCategory(Category category);

    void updateCategory(Category category);

    @Select("SELECT name FROM categories WHERE id = #{id}")
    String selectCategoryNameById(Long id);
}
