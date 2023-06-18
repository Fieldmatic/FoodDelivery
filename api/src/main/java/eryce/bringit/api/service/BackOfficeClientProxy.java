package eryce.bringit.api.service;

import eryce.bringit.shared.clients.BackofficeClient;
import eryce.bringit.shared.model.restaurant.CreateMenuItemRequest;
import eryce.bringit.shared.model.restaurant.CreateRestaurantRequest;
import eryce.bringit.shared.model.restaurant.MenuItemDto;
import eryce.bringit.shared.model.restaurant.RestaurantDto;
import eryce.bringit.shared.model.user.CreateUserRequest;
import eryce.bringit.shared.model.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BackOfficeClientProxy extends ClientProxy {

    private final BackofficeClient backofficeClient;

    private final Logger logger = LoggerFactory.getLogger(BackOfficeClientProxy.class);

    public Optional<UserDto> createUser(CreateUserRequest user) {
        try {
            Response<UserDto> createUserResponse = execute(() -> backofficeClient.createUser(user));
            return handleResponse(createUserResponse);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public Optional<UserDto> getUser(Long userId) {
        try {
            Response<UserDto> getUserResponse = execute(() -> backofficeClient.getUser(userId));
            return handleResponse(getUserResponse);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public Optional<RestaurantDto> createRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        try {
            Response<RestaurantDto> createRestaurantResponse = execute(() -> backofficeClient.createRestaurant(createRestaurantRequest));
            return handleResponse(createRestaurantResponse);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public Optional<RestaurantDto> getRestaurant(Long restaurantId) {
        try {
            Response<RestaurantDto> getRestaurantResponse = execute(() -> backofficeClient.getRestaurant(restaurantId));
            return handleResponse(getRestaurantResponse);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public List<MenuItemDto> getMenuItems(Long restaurantId, List<Long> menuItemIds) {
        try {
            Response<List<MenuItemDto>> getMenuItems = execute(() -> backofficeClient.getMenuItems(restaurantId, menuItemIds));
            return getMenuItems.body();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    public List<MenuItemDto> getMenuItems(Long restaurantId) {
        try {
            Response<List<MenuItemDto>> getMenuItems = execute(() -> backofficeClient.getMenuItems(restaurantId));
            return getMenuItems.body();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    public Optional<MenuItemDto> createMenuItem(Long restaurantId, CreateMenuItemRequest createMenuItemRequest) {
        try {
            Response<MenuItemDto> createMenuItemResponse = execute(() -> backofficeClient.createMenuItem(restaurantId, createMenuItemRequest));
            return handleResponse(createMenuItemResponse);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

}
