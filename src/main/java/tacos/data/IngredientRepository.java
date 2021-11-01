package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
