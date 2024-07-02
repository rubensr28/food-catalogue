package com.applicationone.foodcatalogue.controller;

import com.applicationone.foodcatalogue.dto.FoodCataloguePage;
import com.applicationone.foodcatalogue.dto.FoodItemDto;
import com.applicationone.foodcatalogue.service.FoodCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foodCatalogue")
@CrossOrigin
public class FoodCatalogueController {

    @Autowired
    private FoodCatalogueService foodCatalogueService;

    @PostMapping("/addFoodItem")
    public ResponseEntity<FoodItemDto> addFoodItem(@RequestBody FoodItemDto foodItemDto){
        FoodItemDto foodItemSaved = foodCatalogueService.addFoodItem(foodItemDto);
        return new ResponseEntity<>(foodItemSaved, HttpStatus.CREATED);

    }

    @GetMapping("/fetchRestaurantAndFoodItemsById/{restaurantId}")
    public ResponseEntity<FoodCataloguePage> fetchRestaurantDetailsWithFoodMenu(@PathVariable Long restaurantId){
        FoodCataloguePage foodCataloguePage = this.foodCatalogueService.fetchFoodCataloguePageDetails(restaurantId);
        return new ResponseEntity<FoodCataloguePage>(foodCataloguePage, HttpStatus.OK);
    }
}
