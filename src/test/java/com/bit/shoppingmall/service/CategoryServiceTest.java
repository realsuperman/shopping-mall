package com.bit.shoppingmall.service;

import com.bit.shoppingmall.dao.CategoryDao;
import com.bit.shoppingmall.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

// 트랜잭션 테스트가 필요한 경우에만 RootTest를 extends
public class CategoryServiceTest{
    private CategoryService categoryService;

    @BeforeEach
    public void before(){
      categoryService = new CategoryService(new CategoryDao());
    }

    // select의 경우 예외만 발생안하면 테스트 통과로 가정
    @Test
    void selectAll() {
        List<Category> categories = categoryService.selectAll();
        System.out.println("categories = " + categories);
    }

    @Test
    void selectCategoryById(){
        Long categoryId = 13L;
        Category category = categoryService.findCategoryById(categoryId);
        System.out.println("category = " + category);

    }
}
