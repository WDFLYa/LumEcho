package nuc.edu.lumecho.service.impl;

import nuc.edu.lumecho.mapper.CategoryMapper;
import nuc.edu.lumecho.model.entity.Category;
import nuc.edu.lumecho.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryMapper.getAllCategories();
        return categories;
    }

    @Override
    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }
}
