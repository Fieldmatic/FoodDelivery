package eryce.bringit.shared.clients;

import eryce.bringit.shared.model.order.OrderDto;
import eryce.bringit.shared.model.order.ProcessOrderRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderSearchClient {

    @POST("order")
    Call<OrderDto> saveOrder(@Body ProcessOrderRequest processOrderRequest);

    @GET("order/search/by-userId-and-between-dates/{userId}")
    Call<List<OrderDto>> getOrdersForUserBetweenDates(@Path("userId") Long userId,
                                                      @Query("start") LocalDateTime start,
                                                      @Query("end") LocalDateTime end);


    @GET("order/search/by-userId-and-food/{userId}")
    Call<List<OrderDto>> getOrdersForUserWithFood(@Path("userId") Long userId,
                                                  @Query("foodName") String foodName);

}
