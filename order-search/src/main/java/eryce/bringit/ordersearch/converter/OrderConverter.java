package eryce.bringit.ordersearch.converter;

import eryce.bringit.ordersearch.entity.Order;
import eryce.bringit.shared.model.order.OrderDto;
import eryce.bringit.shared.model.order.ProcessOrderRequest;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    private static final ModelMapper modelMapper = new ModelMapper() {
        {
            getConfiguration().setAmbiguityIgnored(true);
        }
    };

    public static Order toOrder(ProcessOrderRequest processOrderRequest) {
        return modelMapper.map(processOrderRequest, Order.class);
    }

    public static OrderDto toOrderDTO(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setCreatedAt(DateConverter.toDate(order.getCreatedAt()));
        return orderDto;
    }

    public static List<OrderDto> toOrdersDTO(List<Order> orders) {
        return orders.stream().map(OrderConverter::toOrderDTO).collect(Collectors.toList());
    }
}
