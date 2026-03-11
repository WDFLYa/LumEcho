package nuc.edu.lumecho.service;

import nuc.edu.lumecho.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void insertCategory(Category category);
    void updateCategory(Category category);
}
