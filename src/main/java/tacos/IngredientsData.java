package tacos;


import tacos.data.IngredientRepository;

import java.util.*;
import java.util.stream.Collectors;

public class IngredientsData {

    private Map<String, List<Ingredient>> ingredientsMap;

    public IngredientsData(IngredientRepository ingredientRepository) {
        ingredientsMap = new HashMap<>();
        List<Ingredient> ingredients = new ArrayList<>();

        ingredientRepository.findAll().forEach(x -> ingredients.add(x));

        for (Ingredient ingredient : ingredients) {
            ingredientsMap.put(ingredient.getType().toString().toLowerCase(),
                    filterByType(ingredients, ingredient.getType().toString()));
        }
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, String type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    public Map<String, List<Ingredient>> getIngredientsMap() {
        return ingredientsMap;
    }

    public void setIngredientsMap(Map<String, List<Ingredient>> ingredientsMap) {
        this.ingredientsMap = ingredientsMap;
    }
}
