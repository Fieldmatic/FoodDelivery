package eryce.bringit.ordersearch.entity;

import eryce.bringit.shared.model.order.MenuItemOrderDto;
import eryce.bringit.shared.model.order.OrderStatus;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "uuid")
    private String id;
    @Field(type = FieldType.Text)
    private String userName;
    @Field(type = FieldType.Long)
    private Long userId;
    @Field(type = FieldType.Long)
    private Long restaurantId;
    @Field(type = FieldType.Text)
    private String restaurantName;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<MenuItemOrderDto> menuItems;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Field(type = FieldType.Keyword)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;
}
