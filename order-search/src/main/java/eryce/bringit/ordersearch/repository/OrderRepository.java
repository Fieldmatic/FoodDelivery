package eryce.bringit.ordersearch.repository;

import eryce.bringit.ordersearch.entity.Order;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends ElasticsearchRepository<Order, String> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"userId\": \"?0\"}}, {\"range\": {\"createdAt\": {\"gte\": \"?1\", \"lte\": \"?2\"}}}]}}")
    List<Order> findOrdersByUserIdAndCreatedAtBetweenDates(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    //LocalDateTime implicit conversion doesn't work
    //List<Order> findAllByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"userId\": \"?0\"}}, {\"nested\": {\"path\": \"menuItems\", \"query\": {\"bool\": {\"must\": [{\"match\": {\"menuItems.name\": \"?1\"}}]}}}}]}}")
    List<Order> findOrdersByUserIdAndFoodName(Long userId, String foodName);


}
