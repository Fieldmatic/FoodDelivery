package eryce.bringit.shared.clients;

import eryce.bringit.shared.model.restaurant.CreateMenuItemRequest;
import eryce.bringit.shared.model.restaurant.CreateRestaurantRequest;
import eryce.bringit.shared.model.restaurant.MenuItemDto;
import eryce.bringit.shared.model.restaurant.RestaurantDto;
import eryce.bringit.shared.model.user.CreateUserRequest;
import eryce.bringit.shared.model.user.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface BackofficeClient {

    @POST("back-office/user")
    Call<UserDto> createUser(@Body CreateUserRequest createUserRequest);

    @GET("back-office/user/{id}")
    Call<UserDto> getUser(@Path("id") Long userId);

    @POST("back-office/restaurant")
    Call<RestaurantDto> createRestaurant(@Body CreateRestaurantRequest createRestaurantRequest);

    @POST("back-office/restaurant/{id}/menu-item")
    Call<MenuItemDto> createMenuItem(@Path("id") Long restaurantId, @Body CreateMenuItemRequest createMenuItemRequest);

    @GET("back-office/restaurant/{id}")
    Call<RestaurantDto> getRestaurant(@Path("id") Long restaurantId);

    @GET("back-office/restaurant/{id}/menu-items")
    Call<List<MenuItemDto>> getMenuItems(@Path("id") Long restaurantId, @Body List<Long> menuItemIds);

    @GET("back-office/restaurant/{id}/menu-items")
    Call<List<MenuItemDto>> getMenuItems(@Path("id") Long restaurantId);

}
