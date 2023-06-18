package eryce.bringit.ordersearch.service;

import eryce.bringit.ordersearch.converter.OrderConverter;
import eryce.bringit.ordersearch.entity.Order;
import eryce.bringit.ordersearch.repository.OrderRepository;
import eryce.bringit.shared.model.order.ProcessOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order processOrder(ProcessOrderRequest orderRequest) {
        Order order = OrderConverter.toOrder(orderRequest);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUserIdAndFood(Long userId, String foodName) {
        return orderRepository.findOrdersByUserIdAndFoodName(userId, foodName);
    }

    public List<Order> getOrdersByUserIdAndDateBetween(Long userId, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findOrdersByUserIdAndCreatedAtBetweenDates(userId, start, end);
    }
}
