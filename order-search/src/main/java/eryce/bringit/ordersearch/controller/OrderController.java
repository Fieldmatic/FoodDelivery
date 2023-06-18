package eryce.bringit.ordersearch.controller;

import eryce.bringit.ordersearch.converter.OrderConverter;
import eryce.bringit.ordersearch.service.OrderService;
import eryce.bringit.shared.model.order.OrderDto;
import eryce.bringit.shared.model.order.ProcessOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderDto saveOrder(@Valid @RequestBody ProcessOrderRequest processOrderRequest) {
        return OrderConverter.toOrderDTO(orderService.processOrder(processOrderRequest));
    }

    @GetMapping("/search/by-userId-and-between-dates/{userId}")
    public List<OrderDto> getOrdersForUserBetweenDates(@PathVariable(name = "userId") Long userId,
                                                       @NotNull @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                       @NotNull @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return OrderConverter.toOrdersDTO(orderService.getOrdersByUserIdAndDateBetween(userId, start, end));
    }

    @GetMapping("/search/by-userId-and-food/{userId}")
    public List<OrderDto> getOrdersForUserWithFood(@PathVariable(name = "userId") Long userId,
                                                   @NotEmpty @RequestParam(name = "foodName") String foodName

    ) {
        return OrderConverter.toOrdersDTO(orderService.getOrdersByUserIdAndFood(userId, foodName));
    }


}
