package com.example.mediaplatform.cms.api;

import com.example.mediaplatform.cms.application.CategoryService;
import com.example.mediaplatform.cms.dto.CategoryResponse;
import com.example.mediaplatform.cms.dto.CreateCategoryRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping
    public List<CategoryResponse> list() {
        return categoryService.list();
    }
}
