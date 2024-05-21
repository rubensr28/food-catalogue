package com.applicationone.foodcatalogue.service;

import com.applicationone.foodcatalogue.dto.FoodItemDto;
import com.applicationone.foodcatalogue.entity.FoodItem;
import com.applicationone.foodcatalogue.mapper.FoodItemMapper;
import com.applicationone.foodcatalogue.repository.FoodItemRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodCatalogueService {

    @Autowired
    private FoodItemRepository foodItemRepository;


    public FoodItemDto addFoodItem(FoodItemDto foodItemDto) {

        FoodItem foodItemSaved = foodItemRepository.save(
                FoodItemMapper.INSTANCE.mapFoodItemDtoToFoodItem(foodItemDto));

        System.out.println(foodItemDto);

        return FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDto(foodItemSaved);

    }
}
