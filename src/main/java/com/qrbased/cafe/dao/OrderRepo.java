package com.qrbased.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qrbased.cafe.dto.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

	Order findByOrderId(long orderId);

}
