package eryce.bringit.api.controller;

import eryce.bringit.api.service.BackOfficeClientProxy;
import eryce.bringit.api.service.OrderProcessingClientProxy;
import eryce.bringit.api.service.OrderProcessingUtil;
import eryce.bringit.api.service.OrderSearchClientProxy;
import eryce.bringit.shared.model.order.*;
import eryce.bringit.shared.model.restaurant.RestaurantDto;
import eryce.bringit.shared.model.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-processing")
@RequiredArgsConstructor
public class OrderProcessingController {

    private final BackOfficeClientProxy backOfficeClientProxy;
    private final OrderProcessingClientProxy orderProcessingClientProxy;
    private final OrderProcessingUtil orderProcessingUtil;
    private final OrderSearchClientProxy orderSearchClientProxy;

    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {

        Optional<UserDto> userOptional = backOfficeClientProxy.getUser(createOrderRequest.getUserId());
        Optional<RestaurantDto> restaurantOptional = backOfficeClientProxy.getRestaurant(createOrderRequest.getRestaurantId());

        boolean dataIsPresent = userOptional.isPresent() && restaurantOptional.isPresent();

        if (dataIsPresent) {

            List<MenuItemOrderDto> menuItems = orderProcessingUtil.convertToOrderMenuItems(createOrderRequest.getMenuItems(), restaurantOptional.get());

            if (!menuItems.isEmpty()) {
                return processOrder(userOptional.get(), restaurantOptional.get(), menuItems);
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String id) {
        return ResponseEntity.of(orderProcessingClientProxy.getOrder(id));
    }

    private ResponseEntity<OrderDto> processOrder(UserDto user, RestaurantDto restaurant, List<MenuItemOrderDto> menuItems) {
        ProcessOrderRequest processOrderRequest = orderProcessingUtil.buildProcessOrderRequest(user, restaurant, menuItems);

        Optional<OrderDto> newOrder = orderProcessingClientProxy.createOrder(processOrderRequest);
        Optional<OrderDto> newOrderElastic = orderSearchClientProxy.saveOrder(processOrderRequest);

        if (newOrder.isPresent() && newOrderElastic.isPresent()) {
            return ResponseEntity.ok(newOrder.get());
        }

        return ResponseEntity.badRequest().build();
    }


    @PutMapping("/order/{id}/status")
    public ResponseEntity<OrderStatus> updateOrderStauts(@PathVariable String id, @Valid @RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest) {

        OrderStatus updatedStatus = orderProcessingClientProxy.updateOrderStatus(id, orderStatusUpdateRequest);
        if (updatedStatus != null) {
            return ResponseEntity.ok(updatedStatus);
        }

        return ResponseEntity.badRequest().build();

    }


}