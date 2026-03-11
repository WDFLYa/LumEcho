package nuc.edu.lumecho.controller.category;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.entity.Category;
import nuc.edu.lumecho.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public Result<List< Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return Result.ok(categories);
    }

    public Result insertCategory(Category category) {
        categoryService.insertCategory(category);
        return Result.ok();
    }

    public Result updateCategory(Category category) {
        categoryService.updateCategory(category);
        return Result.ok();
    }

}
