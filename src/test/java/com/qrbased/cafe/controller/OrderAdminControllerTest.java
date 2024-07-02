package com.qrbased.cafe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qrbased.cafe.dto.Order;
import com.qrbased.cafe.exception.ApiResponse;
import com.qrbased.cafe.exception.ForbiddenException;
import com.qrbased.cafe.security.CustomUserDetailsService;
import com.qrbased.cafe.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderAdminControllerTest {

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private OrderAdminController orderAdminController;

    @Test
    void testGetAllOrders() {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        Order order1 = new Order(/* construct your Order object */);
        Order order2 = new Order(/* construct your Order object */);
        List<Order> orders = Arrays.asList(order1, order2);
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> responseEntity = orderAdminController.getAllOrders();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orders, responseEntity.getBody());

        verify(customUserDetailsService).hasAnyRole("ADMIN");
        verify(orderService).getAllOrders();
    }

    @Test
    void testDeleteAllOrders() {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        ResponseEntity<ApiResponse> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(), "All orders deleted successfully"));
        when(orderService.deleteAllOrders()).thenReturn(expectedResponseEntity.getBody());

        ResponseEntity<ApiResponse> responseEntity = orderAdminController.deleteAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody(), responseEntity.getBody());

        verify(customUserDetailsService).hasAnyRole("ADMIN");
        verify(orderService).deleteAllOrders();
    }

    @Test
    void testDeleteOrder() {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(true);

        long orderId = 123L;
        Order deletedOrder = new Order(/* construct your Order object */);
        when(orderService.deleteOrder(orderId)).thenReturn(deletedOrder);

        ResponseEntity<Order> responseEntity = orderAdminController.deleteFood(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(deletedOrder, responseEntity.getBody());

        verify(customUserDetailsService).hasAnyRole("ADMIN");
        verify(orderService).deleteOrder(orderId);
    }

    @Test
    void testGetAllOrdersForbidden() {
        when(customUserDetailsService.hasAnyRole("ADMIN")).thenReturn(false);

        ForbiddenException exception = org.junit.jupiter.api.Assertions.assertThrows(ForbiddenException.class, () -> {
            orderAdminController.getAllOrders();
        });

        assertEquals("You do not have permission to access this resource", exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN.value(), exception.getRespondeCode());

        verify(customUserDetailsService).hasAnyRole("ADMIN");
        verifyNoInteractions(orderService);
    }

    // Similar tests for deleteAll and deleteOrder methods with ForbiddenException
}

