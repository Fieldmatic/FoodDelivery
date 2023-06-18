package eryce.bringit.api.service;

import eryce.bringit.shared.clients.OrderSearchClient;
import eryce.bringit.shared.model.order.OrderDto;
import eryce.bringit.shared.model.order.ProcessOrderRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderSearchClientProxy extends ClientProxy {

    private final OrderSearchClient orderSearchClient;

    private final Logger logger = LoggerFactory.getLogger(OrderSearchClientProxy.class);

    public Optional<OrderDto> saveOrder(ProcessOrderRequest processOrderRequest) {
        try {
            Response<OrderDto> createOrderResponse = execute(() -> orderSearchClient.saveOrder(processOrderRequest));
            return handleResponse(createOrderResponse);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public Optional<List<OrderDto>> getOrdersForUserBetweenDates(Long userId, LocalDateTime start, LocalDateTime end) {
        try {
            Response<List<OrderDto>> createOrderResponse = execute(() -> orderSearchClient.getOrdersForUserBetweenDates(userId, start, end));
            return handleResponse(createOrderResponse);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public Optional<List<OrderDto>> getOrdersForUserWithFood(Long userId, String foodName) {
        try {
            Response<List<OrderDto>> createOrderResponse = execute(() -> orderSearchClient.getOrdersForUserWithFood(userId, foodName));
            return handleResponse(createOrderResponse);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }


}