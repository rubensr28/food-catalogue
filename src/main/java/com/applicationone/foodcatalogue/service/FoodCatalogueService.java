package com.applicationone.foodcatalogue.service;

import com.applicationone.foodcatalogue.dto.FoodCataloguePage;
import com.applicationone.foodcatalogue.dto.FoodItemDto;
import com.applicationone.foodcatalogue.dto.Restaurant;
import com.applicationone.foodcatalogue.entity.FoodItem;
import com.applicationone.foodcatalogue.mapper.FoodItemMapper;
import com.applicationone.foodcatalogue.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;

@Service
public class FoodCatalogueService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Value("${restaurant.microservice}")
    private String restaurantMsName;

    @Autowired
    private RestTemplate restTemplate;


    public FoodItemDto addFoodItem(FoodItemDto foodItemDto) {

        FoodItem foodItemSaved = foodItemRepository.save(
                FoodItemMapper.INSTANCE.mapFoodItemDtoToFoodItem(foodItemDto));

        System.out.println(foodItemDto);

        return FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDto(foodItemSaved);

    }

    public FoodCataloguePage fetchFoodCataloguePageDetails(Long restaurantId) {
        List<FoodItem> foodItemList = fetchFoodItemList(restaurantId);
        Restaurant restaurant = fetchRestaurantDetails(restaurantId);
        return createFoodCataloguePage(foodItemList, restaurant);
    }

    private FoodCataloguePage createFoodCataloguePage(List<FoodItem> foodItemList, Restaurant restaurant) {
        FoodCataloguePage foodCataloguePage = new FoodCataloguePage();
        foodCataloguePage.setFoodItemsList(this.fetchFoodItemList(restaurant.getId()));
        foodCataloguePage.setRestaurant(restaurant);
        return foodCataloguePage;
    }

    private Restaurant fetchRestaurantDetails(Long restaurantId) {
        String uri = MessageFormat.format("http://{0}/restaurant/fetchById/{1}", restaurantMsName, restaurantId);
        return restTemplate.getForObject(uri, Restaurant.class);
    }

    private List<FoodItem> fetchFoodItemList(Long restaurantId) {
        return foodItemRepository.findByRestaurantId(restaurantId);
    }
}
