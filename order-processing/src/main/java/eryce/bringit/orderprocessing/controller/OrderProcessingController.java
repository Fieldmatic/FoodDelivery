package eryce.bringit.orderprocessing.controller;

import eryce.bringit.orderprocessing.service.OrderService;
import eryce.bringit.shared.model.order.OrderDto;
import eryce.bringit.shared.model.order.OrderStatus;
import eryce.bringit.shared.model.order.OrderStatusUpdateRequest;
import eryce.bringit.shared.model.order.ProcessOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/order-processing")
@RequiredArgsConstructor
public class OrderProcessingController {

    private final OrderService orderService;

    @PostMapping
    @RequestMapping("/order")
    public ResponseEntity<OrderDto> processOrder(@Valid @RequestBody ProcessOrderRequest processOrderRequest) {

        Optional<OrderDto> newOrderOptional = orderService.processOrderRequest(processOrderRequest);

        return ResponseEntity.of(newOrderOptional);

    }

    @GetMapping
    @RequestMapping("/order/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String id) {

        Optional<OrderDto> newOrderOptional = orderService.getOrder(id);

        return ResponseEntity.of(newOrderOptional);

    }

    @PutMapping
    @RequestMapping("/order/{id}/status")
    public ResponseEntity<OrderStatus> updateOrderStatus(@PathVariable String id, @Valid @RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest) {

        boolean statusUpdated = orderService.updateOrderStatus(id, orderStatusUpdateRequest);

        return statusUpdated ? ResponseEntity.ok(orderStatusUpdateRequest.getStatus()) : ResponseEntity.badRequest().build();
    }

}
