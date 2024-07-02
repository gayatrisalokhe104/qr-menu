package com.qrbased.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import com.qrbased.cafe.dto.Food;
import com.qrbased.cafe.security.CustomUserDetailsService;
import com.qrbased.cafe.service.impl.FoodServiceImpl;
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
class FoodControllerTest {

    @Mock
    private FoodServiceImpl foodService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private FoodController foodController;

    private MockMvc mockMvc;

    @Test
    void createFood_shouldReturnCreated() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        Food food = new Food();
        when(foodService.createFood(any(), anyLong())).thenReturn(food); // Change anyInt() to anyLong()

        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();

        mockMvc.perform(post("/api/v1/admin/food/category/1/create-food")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").doesNotExist());

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(foodService, times(1)).createFood(any(), anyLong()); // Change anyInt() to anyLong()
    }


    @Test
    void createFood_shouldReturnForbidden() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(false);

        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();

        mockMvc.perform(post("/api/v1/admin/food/category/1/create-food")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isForbidden());

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(foodService, never()).createFood(any(), anyInt());
    }

    @Test
    void updateFood_shouldReturnOk() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        Food food = new Food();
        when(foodService.updateFood(anyLong(), any())).thenReturn(food);

        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();

        mockMvc.perform(put("/api/v1/admin/food/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").doesNotExist()); // Customize this based on your Food object

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(foodService, times(1)).updateFood(anyLong(), any());
    }

    @Test
    void deleteFood_shouldReturnOk() throws Exception {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        Food food = new Food();
        when(foodService.deleteFood(anyLong())).thenReturn(food);

        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();

        mockMvc.perform(delete("/api/v1/admin/food/1/delete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").doesNotExist()); // Customize this based on your Food object

        verify(customUserDetailsService, times(1)).hasAnyRole("ADMIN");
        verify(foodService, times(1)).deleteFood(anyLong());
    }

}

