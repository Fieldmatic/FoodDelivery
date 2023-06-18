package eryce.bringit.orderprocessing.entity;

import eryce.bringit.shared.model.order.MenuItemOrderDto;
import eryce.bringit.shared.model.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private ObjectId id;

    private Long userId;
    private String userName;
    private Long restaurantId;
    private String restaurantName;
    private Double price;
    private List<MenuItemOrderDto> menuItems;
    private Date createdAt = new Date();
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;
}
