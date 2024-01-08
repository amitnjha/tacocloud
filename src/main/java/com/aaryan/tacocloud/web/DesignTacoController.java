package com.aaryan.tacocloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.aaryan.tacocloud.Ingredient;
import com.aaryan.tacocloud.Taco;
import com.aaryan.tacocloud.TacoOrder;
import com.aaryan.tacocloud.Ingredient.Type;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    
    @PostMapping("/design")
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder){

        if(errors.hasErrors()){
            return "/design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing Taco: {}", taco);

        return "redirect:/orders/current";
    }

    static List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("FLTO", "Floor Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("PITO", "Pita Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("CHIC", "Chicken", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("PICK", "Pickles", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE),
            new Ingredient("CILA", "Cilantro", Type.SAUCE),
            new Ingredient("AAPL", "Apple", Type.FRUIT),
            new Ingredient("ORNG", "Orange", Type.FRUIT)


        );

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        
        Type[] types = Ingredient.Type.values();
        
        for(Type type: types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
        return ingredients
                .stream()
                .filter( x -> x.getType().equals(type))
                .collect(Collectors.toList());

    }

    @ModelAttribute( name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute( name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping("/design")
    public String showDesignForm(){
        return "design.html";
    }
}
