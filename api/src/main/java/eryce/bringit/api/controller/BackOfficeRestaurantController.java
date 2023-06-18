package eryce.bringit.api.controller;

import eryce.bringit.api.service.BackOfficeClientProxy;
import eryce.bringit.shared.model.restaurant.CreateMenuItemRequest;
import eryce.bringit.shared.model.restaurant.CreateRestaurantRequest;
import eryce.bringit.shared.model.restaurant.MenuItemDto;
import eryce.bringit.shared.model.restaurant.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/back-office/")
@RequiredArgsConstructor
public class BackOfficeRestaurantController {

    private final BackOfficeClientProxy backOfficeClientProxy;

    @PostMapping("/restaurant")
    public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {
        Optional<RestaurantDto> newRestaurant = backOfficeClientProxy.createRestaurant(createRestaurantRequest);
        if (newRestaurant.isPresent()) {
            return ResponseEntity.ok(newRestaurant.get());
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable Long id) {
        Optional<RestaurantDto> restaurant = backOfficeClientProxy.getRestaurant(id);

        return ResponseEntity.of(restaurant);
    }

    @GetMapping("/restaurant/{id}/menu-items")
    public ResponseEntity<List<MenuItemDto>> getMenuItems(@PathVariable Long id) {
        List<MenuItemDto> menuItems = backOfficeClientProxy.getMenuItems(id);

        return ResponseEntity.ok(menuItems);
    }

    @PostMapping("/restaurant/{id}/menu-item")
    public ResponseEntity<MenuItemDto> createMenuItem(@PathVariable("id") Long restaurantId, @Valid @RequestBody CreateMenuItemRequest createMenuItemRequest) {
        Optional<MenuItemDto> newMenuItem = backOfficeClientProxy.createMenuItem(restaurantId, createMenuItemRequest);
        if (newMenuItem.isPresent()) {
            return ResponseEntity.ok(newMenuItem.get());
        }

        return ResponseEntity.badRequest().build();
    }
}
