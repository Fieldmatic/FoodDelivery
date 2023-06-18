package eryce.bringit.backoffice.controller;

import eryce.bringit.backoffice.service.RestaurantService;
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
@RequestMapping("/back-office")
@RequiredArgsConstructor
public class BackOfficeRestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant")
    public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {

        return ResponseEntity.ok(restaurantService.createRestaurant(createRestaurantRequest));
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable Long id) {

        return ResponseEntity.of(restaurantService.getRestaurant(id));
    }

    @GetMapping("/restaurant/{id}/menu-items")
    public ResponseEntity<List<MenuItemDto>> getMenuItems(@PathVariable Long id) {

        return ResponseEntity.ok(restaurantService.getMenuItems(id));
    }

    @PostMapping("/restaurant/{id}/menu-item")
    public ResponseEntity<MenuItemDto> createMenuItem(@PathVariable("id") Long restaurantId, @Valid @RequestBody CreateMenuItemRequest createMenuItemRequest) {

        Optional<MenuItemDto> menuItem = restaurantService.createMenuItem(restaurantId, createMenuItemRequest);

        return menuItem.isPresent() ? ResponseEntity.ok(menuItem.get()) : ResponseEntity.badRequest().build();

    }
}
