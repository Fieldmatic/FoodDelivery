package eryce.bringit.api.service;

import eryce.bringit.shared.model.order.MenuItemOrderDto;
import eryce.bringit.shared.model.order.MenuItemProcessOrderRequest;
import eryce.bringit.shared.model.order.ProcessOrderRequest;
import eryce.bringit.shared.model.restaurant.MenuItemDto;
import eryce.bringit.shared.model.restaurant.RestaurantDto;
import eryce.bringit.shared.model.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProcessingUtil {

    public ProcessOrderRequest buildProcessOrderRequest(UserDto user, RestaurantDto restaurant, List<MenuItemOrderDto> menuItems) {

        return ProcessOrderRequest.builder()
                .userName(user.getName())
                .userId(user.getId())
                .restaurantId(restaurant.getId())
                .restaurantName(restaurant.getName())
                .deliveryRate(restaurant.getDeliveryRate())
                .menuItems(menuItems)
                .build();
    }

    public List<MenuItemOrderDto> convertToOrderMenuItems(List<MenuItemProcessOrderRequest> menuItemProcessOrderRequests, RestaurantDto restaurant) {
        return menuItemProcessOrderRequests.stream()
                .filter(menuItem -> restaurant.getMenuItems().stream().anyMatch(i -> i.getId() == menuItem.getId()))
                .map(menuItem -> {
                    MenuItemDto menuItemDto = restaurant.getMenuItems().stream().filter(i -> i.getId() == menuItem.getId()).findFirst().get();
                    return MenuItemOrderDto.builder()
                            .count(menuItem.getCount())
                            .id(menuItem.getId())
                            .description(menuItemDto.getDescription())
                            .name(menuItemDto.getDescription())
                            .price(menuItemDto.getPrice())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
