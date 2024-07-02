package com.qrbased.cafe.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.qrbased.cafe.dto.Order;

@ExtendWith(MockitoExtension.class)
public class OrderRepoTest {
	@Mock
	private OrderRepo orderRepo;

	@Test
	void testFindByOrderId() {
		long orderId = 1L;
		Order order = new Order();
		order.setOrderId(orderId);
		when(orderRepo.findByOrderId(orderId)).thenReturn(order);

		Order foundOrder = orderRepo.findByOrderId(orderId);

		assertNotNull(foundOrder);
		assertEquals(order, foundOrder);
		verify(orderRepo, times(1)).findByOrderId(orderId);
	}

	@Test
	void testFindByOrderIdNotFound() {
		long orderId = 1L;
		when(orderRepo.findByOrderId(orderId)).thenReturn(null);

		Order foundOrder = orderRepo.findByOrderId(orderId);

		assertNull(foundOrder);
		verify(orderRepo, times(1)).findByOrderId(orderId);
	}
}
