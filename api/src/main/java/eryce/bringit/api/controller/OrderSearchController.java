package eryce.bringit.api.controller;


import eryce.bringit.api.service.OrderSearchClientProxy;
import eryce.bringit.shared.model.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/order-search")
@RequiredArgsConstructor
public class OrderSearchController {

    private final OrderSearchClientProxy orderSearchClientProxy;

    @GetMapping("/by-userId-and-between-dates/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersForUserBetweenDates(@PathVariable(name = "userId") Long userId,
                                                                       @NotNull @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                       @NotNull @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.of(orderSearchClientProxy.getOrdersForUserBetweenDates(userId, start, end));
    }

    @GetMapping("/by-userId-and-food/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersForUserWithFood(@PathVariable(name = "userId") Long userId,
                                                                   @NotEmpty @RequestParam(name = "foodName") String foodName) {
        return ResponseEntity.of(orderSearchClientProxy.getOrdersForUserWithFood(userId, foodName));
    }
}
