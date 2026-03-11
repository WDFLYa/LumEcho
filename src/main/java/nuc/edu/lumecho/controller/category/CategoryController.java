package nuc.edu.lumecho.controller.category;


import nuc.edu.lumecho.common.Result;
import nuc.edu.lumecho.model.entity.Category;
import nuc.edu.lumecho.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public Result<List< Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return Result.ok(categories);
    }

    @PostMapping("/insertCategory")
    public Result insertCategory(@RequestBody Category category) {
        categoryService.insertCategory(category);
        return Result.ok();
    }

    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return Result.ok();
    }

}
