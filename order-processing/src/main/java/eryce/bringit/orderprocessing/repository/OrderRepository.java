package eryce.bringit.orderprocessing.repository;

import eryce.bringit.orderprocessing.entity.Order;
import eryce.bringit.shared.model.order.OrderStatus;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> createOrder(Order order);

    Optional<Order> findById(String id);

    boolean updateStatus(String id, OrderStatus status);
}
