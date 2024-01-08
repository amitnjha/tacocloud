package com.aaryan.tacocloud.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.aaryan.tacocloud.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private Map<String, Ingredient> ingredeintMap = new HashMap<String, Ingredient>();

    public IngredientByIdConverter(){
        DesignTacoController.ingredients.forEach(ingr -> ingredeintMap.put(ingr.getId(), ingr));
    }

    @Override
    public Ingredient convert(String id){
        return ingredeintMap.get(id);
    }
}
