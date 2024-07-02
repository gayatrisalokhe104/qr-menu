package com.qrbased.cafe.service;

import java.util.List;
import com.qrbased.cafe.dto.Category;

public interface CategoryService {
   Category createCategory(Category category);
   Category updateCategory(long id,Category category);
   Category deleteCategory(long id);
   List<Category> getCategories();
}
