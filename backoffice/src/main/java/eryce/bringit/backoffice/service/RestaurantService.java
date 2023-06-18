package eryce.bringit.backoffice.service;

import eryce.bringit.backoffice.entity.MenuItem;
import eryce.bringit.backoffice.entity.Restaurant;
import eryce.bringit.backoffice.repository.MenuItemRepository;
import eryce.bringit.backoffice.repository.RestaurantRepository;
import eryce.bringit.shared.model.restaurant.CreateMenuItemRequest;
import eryce.bringit.shared.model.restaurant.CreateRestaurantRequest;
import eryce.bringit.shared.model.restaurant.MenuItemDto;
import eryce.bringit.shared.model.restaurant.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public List<MenuItemDto> getMenuItems(List<Long> menuItemIds) {

        List<MenuItem> menuItems = menuItemRepository.findAllById(menuItemIds);

        return menuItems.stream().map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class)).collect(Collectors.toList());

    }

    public List<MenuItemDto> getMenuItems(Long restaurantId) {

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);

        return restaurantOptional.map(restaurant -> restaurant.getMenuItems().stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public Optional<RestaurantDto> getRestaurant(Long restaurantId) {

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);

        return restaurantOptional.map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class))
                .or(Optional::empty);
    }

    public RestaurantDto createRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        Restaurant restaurant = modelMapper.map(createRestaurantRequest, Restaurant.class);
        List<MenuItem> menuItems = createRestaurantRequest.getMenuItems().stream()
                .map(menuItemRequest -> MenuItem.builder()
                        .description(menuItemRequest.getDescription())
                        .name(menuItemRequest.getName())
                        .price(menuItemRequest.getPrice())
                        .restaurant(restaurant)
                        .build())
                .collect(Collectors.toList());

        restaurant.setMenuItems(menuItems);
        Restaurant newRestaurant = restaurantRepository.save(restaurant);

        return modelMapper.map(newRestaurant, RestaurantDto.class);
    }

    public Optional<MenuItemDto> createMenuItem(Long restaurantId, CreateMenuItemRequest createMenuItemRequest) {
        MenuItem menuItem = modelMapper.map(createMenuItemRequest, MenuItem.class);

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isPresent()) {
            menuItem.setRestaurant(restaurantOptional.get());
            MenuItem savedItem = menuItemRepository.save(menuItem);
            return Optional.of(modelMapper.map(savedItem, MenuItemDto.class));
        }

        return Optional.empty();
    }
}
