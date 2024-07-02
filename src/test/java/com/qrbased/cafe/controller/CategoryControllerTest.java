package com.qrbased.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.security.CustomUserDetailsService;
import com.qrbased.cafe.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @Test
    void createCategory_shouldReturnCreated() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        Category category = new Category();
        when(categoryService.createCategory(any())).thenReturn(category);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(post("/api/v1/admin/category/create-category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").doesNotExist());

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(categoryService, times(1)).createCategory(any());
    }

    @Test
    void createCategory_shouldReturnForbidden() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(false);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(post("/api/v1/admin/category/create-category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isForbidden());

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(categoryService, never()).createCategory(any());
    }
    
    @Test
    void updateCategory_shouldReturnOk() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        Category category = new Category();
        when(categoryService.updateCategory(anyLong(), any())).thenReturn(category);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(put("/api/v1/admin/category/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").doesNotExist());

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(categoryService, times(1)).updateCategory(anyLong(), any());
    }

    @Test
    void deleteCategory_shouldReturnOk() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        Category category = new Category();
        when(categoryService.deleteCategory(anyLong())).thenReturn(category);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(delete("/api/v1/admin/category/1/delete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").doesNotExist());

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(categoryService, times(1)).deleteCategory(anyLong());
    }

    @Test
    void getMenu_shouldReturnOk() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        Category category1 = new Category();
        Category category2 = new Category();
        when(categoryService.getCategories()).thenReturn(Arrays.asList(category1, category2));

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(get("/api/v1/admin/category/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").doesNotExist()); // Customize this based on your Category object

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(categoryService, times(1)).getCategories();
    }

}

