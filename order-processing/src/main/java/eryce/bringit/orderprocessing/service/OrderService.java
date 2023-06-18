package eryce.bringit.orderprocessing.service;

import eryce.bringit.orderprocessing.entity.Order;
import eryce.bringit.orderprocessing.repository.OrderRepository;
import eryce.bringit.shared.model.order.MenuItemOrderDto;
import eryce.bringit.shared.model.order.OrderDto;
import eryce.bringit.shared.model.order.OrderStatusUpdateRequest;
import eryce.bringit.shared.model.order.ProcessOrderRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public Optional<OrderDto> processOrderRequest(ProcessOrderRequest processOrderRequest) {

        Order order = modelMapper.map(processOrderRequest, Order.class);
        order.setPrice(getOrderTotalPrice(processOrderRequest.getMenuItems(), processOrderRequest.getDeliveryRate()));

        Optional<Order> newOrderOptional = orderRepository.createOrder(order);

        return newOrderOptional.map(this::mapOrderToOrderDto);
    }

    public Optional<OrderDto> getOrder(String orderId) {
        return orderRepository.findById(orderId).map(this::mapOrderToOrderDto);
    }

    public boolean updateOrderStatus(String orderId, OrderStatusUpdateRequest orderStatusUpdateRequest) {
        return orderRepository.updateStatus(orderId, orderStatusUpdateRequest.getStatus());
    }

    private Double getOrderTotalPrice(List<MenuItemOrderDto> menuItems, Double deliveryRate) {
        return menuItems.stream()
                .reduce(0.0, (total, item) -> total + item.getPrice() * item.getCount(), Double::sum) + deliveryRate;
    }

    private OrderDto mapOrderToOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setOrderId(order.getId().toString());
        return orderDto;
    }
}
