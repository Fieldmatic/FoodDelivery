package eryce.bringit.backoffice.service;

import eryce.bringit.backoffice.entity.MenuItem;
import eryce.bringit.backoffice.entity.Restaurant;
import eryce.bringit.backoffice.repository.MenuItemRepository;
import eryce.bringit.backoffice.repository.RestaurantRepository;
import eryce.bringit.shared.model.restaurant.CreateMenuItemRequest;
import eryce.bringit.shared.model.restaurant.CreateRestaurantRequest;
import eryce.bringit.shared.model.restaurant.MenuItemDto;
import eryce.bringit.shared.model.restaurant.RestaurantDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTests {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Captor
    private ArgumentCaptor<Restaurant> restaurantCaptor;

    @Test
    void createMenuItem_restaurantNotFound_returnEmpty() {
        Long restaurantId = 1L;
        CreateMenuItemRequest request = CreateMenuItemRequest.builder()
                .build();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        Optional<MenuItemDto> result = restaurantService.createMenuItem(restaurantId, request);

        assertEquals(Optional.empty(), result);
        verify(restaurantRepository, Mockito.times(1)).findById(restaurantId);
        verifyNoInteractions(menuItemRepository);
    }

    @Test
    void createMenuItem_restaurantFound_menuItemCreated() {
        CreateMenuItemRequest request = createSampleBurgerMenuItemRequest();
        Restaurant restaurant = createSampleRestaurant();
        MenuItem menuItem = createSampleMenuItem();
        MenuItem savedMenuItem = createSampleSavedMenuItem(request, restaurant);

        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.save(menuItem)).thenReturn(savedMenuItem);
        when(modelMapper.map(request, MenuItem.class)).thenReturn(menuItem);

        MenuItemDto expectedMenuItemDto = MenuItemDto.builder()
                .id(2L)
                .name("Burger")
                .price(500.00)
                .build();

        Optional<MenuItemDto> menuItemDtoResult = restaurantService.createMenuItem(restaurant.getId(), request);
        assertTrue(menuItemDtoResult.isPresent());

        MenuItemDto menuItemResult = menuItemDtoResult.get();
        assertEquals(expectedMenuItemDto.getId(), menuItemResult.getId());
        assertEquals(expectedMenuItemDto.getName(), menuItemResult.getName());
        assertEquals(expectedMenuItemDto.getPrice(), menuItemResult.getPrice());
        verify(restaurantRepository, Mockito.times(1)).findById(restaurant.getId());
    }

    private static MenuItem createSampleSavedMenuItem(CreateMenuItemRequest createMenuItemRequest, Restaurant restaurant) {
        return MenuItem.builder()
                .id(2L)
                .name(createMenuItemRequest.getName())
                .description(createMenuItemRequest.getDescription())
                .price(createMenuItemRequest.getPrice())
                .restaurant(restaurant)
                .build();
    }

    private static MenuItem createSampleMenuItem() {
        return MenuItem.builder()
                .name("Burger")
                .price(500.00)
                .description("Odlican")
                .build();
    }

    private static Restaurant createSampleRestaurant() {
        return Restaurant.builder()
                .id(1L)
                .name("Restoran")
                .phone("066")
                .address("Adresa")
                .email("restaurant@gmail.com")
                .deliveryRate(200.00)
                .build();
    }

    private static CreateMenuItemRequest createSampleBurgerMenuItemRequest() {
        return CreateMenuItemRequest.builder()
                .name("Burger")
                .price(500.00)
                .description("Odlican")
                .build();
    }

    @Test
    public void createRestaurant_success() {
        CreateMenuItemRequest menuItemRequestBurger = createSampleBurgerMenuItemRequest();
        CreateMenuItemRequest menuItemRequestPizza = createSamplePizzaMenuItemRequest();
        CreateRestaurantRequest createRestaurantRequest = createSampleRestaurantRequest(Arrays.asList(menuItemRequestBurger, menuItemRequestPizza));
        Restaurant restaurant = createSampleRestaurant();
        List<MenuItem> menuItems = createSampleMenuItems(restaurant);
        Restaurant savedRestaurant = createSampleSavedRestaurant(menuItems);

        when(modelMapper.map(createRestaurantRequest, Restaurant.class)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurantCaptor.capture())).thenReturn(savedRestaurant);

        RestaurantDto newRestaurant = restaurantService.createRestaurant(createRestaurantRequest);

        assertNotNull(newRestaurant);
        verify(restaurantRepository).save(restaurantCaptor.capture());
        verify(restaurantRepository, Mockito.times(1)).save(restaurant);
        assertEquals(restaurant, restaurantCaptor.getValue());
        assertEquals(createRestaurantRequest.getName(), restaurantCaptor.getValue().getName());
        assertEquals(createRestaurantRequest.getAddress(), restaurantCaptor.getValue().getAddress());
        assertEquals(createRestaurantRequest.getPhone(), restaurantCaptor.getValue().getPhone());
        assertEquals(createRestaurantRequest.getEmail(), restaurantCaptor.getValue().getEmail());
        assertEquals(createRestaurantRequest.getDeliveryRate(), restaurantCaptor.getValue().getDeliveryRate());
        assertEquals(restaurant.getName(), newRestaurant.getName());
        assertEquals(restaurant.getEmail(), newRestaurant.getEmail());
        assertEquals(restaurant.getAddress(), newRestaurant.getAddress());
        assertEquals(restaurant.getDeliveryRate(), newRestaurant.getDeliveryRate());
        assertEquals(restaurant.getMenuItems().size(), newRestaurant.getMenuItems().size());
    }

    private static Restaurant createSampleSavedRestaurant(List<MenuItem> menuItems) {
        return Restaurant.builder()
                .id(1L)
                .email("restaurant@gmail.com")
                .address("Adresa")
                .name("Restoran")
                .deliveryRate(200.00)
                .menuItems(menuItems)
                .build();
    }

    private static List<MenuItem> createSampleMenuItems(Restaurant restaurant) {
        MenuItem menuItemBurger = MenuItem.builder()
                .id(1L)
                .name("Burger")
                .price(500.00)
                .description("Odlican")
                .restaurant(restaurant)
                .build();
        MenuItem menuItemPizza = MenuItem.builder()
                .id(2L)
                .name("Pizza")
                .price(700.00)
                .description("Odlican")
                .restaurant(restaurant)
                .build();
        return Arrays.asList(menuItemBurger, menuItemPizza);
    }

    private static CreateRestaurantRequest createSampleRestaurantRequest(List<CreateMenuItemRequest> createMenuItemRequests) {
        return CreateRestaurantRequest.builder()
                .email("restaurant@gmail.com")
                .address("Adresa")
                .name("Restoran")
                .phone("066")
                .deliveryRate(200.00)
                .menuItems(createMenuItemRequests)
                .build();
    }

    private static CreateMenuItemRequest createSamplePizzaMenuItemRequest() {
        return CreateMenuItemRequest.builder()
                .name("Pizza")
                .price(700.00)
                .description("Odlican")
                .build();
    }

    @Test
    public void getMenuItems_restaurantNotFound_returnEmpty() {
        Restaurant restaurant = createSampleRestaurant();
        List<MenuItem> menuItems = createSampleMenuItems(restaurant);
        MenuItemDto expectedMenuItemDtoBurger = MenuItemDto.builder()
                .id(1L)
                .name("Burger")
                .price(500.00)
                .description("Odlican")
                .build();
        MenuItemDto expectedMenuItemDtoPizza = MenuItemDto.builder()
                .id(2L)
                .name("Pizza")
                .price(700.00)
                .description("Odlican")
                .build();

        List<MenuItemDto> expectedMenuItemsDto = Arrays.asList(expectedMenuItemDtoBurger, expectedMenuItemDtoPizza);
        restaurant.setMenuItems(menuItems);

        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(modelMapper.map(menuItems.get(0), MenuItemDto.class)).thenReturn(expectedMenuItemDtoBurger);
        when(modelMapper.map(menuItems.get(1), MenuItemDto.class)).thenReturn(expectedMenuItemDtoPizza);

        List<MenuItemDto> menuItemsDTO = restaurantService.getMenuItems(restaurant.getId());
        assertEquals(expectedMenuItemsDto, menuItemsDTO);
        verify(restaurantRepository, Mockito.times(1)).findById(restaurant.getId());
    }

    @Test
    public void getMenuItems_restaurantFound_returnRestaurantList() {
        long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        List<MenuItemDto> menuItemsDTO = restaurantService.getMenuItems(restaurantId);
        assertNotNull(menuItemsDTO);
        assertTrue(menuItemsDTO.isEmpty());
        verify(restaurantRepository, Mockito.times(1)).findById(restaurantId);
        verifyNoInteractions(modelMapper);
    }
}
