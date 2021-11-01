package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import tacos.IngredientsData;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(path="/design", produces = "application/json")
@CrossOrigin(origins = "*")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final TacoModelAssembler tacoModelAssembler;

    @Autowired
    public DesignTacoController (IngredientRepository ingredientRepository,
                                 TacoRepository tacoRepository,
                                 TacoModelAssembler tacoModelAssembler) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.tacoModelAssembler = tacoModelAssembler;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "ingredientsData")
    public IngredientsData ingredientsData() {
        return new IngredientsData(ingredientRepository);
    }

    @GetMapping
    public String showDesignForm() {
//        List<Ingredient> ingredients = new ArrayList<>();
//        ingredientRepository.findAll().forEach(x -> ingredients.add(x));
//
//        Type[] types = Ingredient.Type.values();
//        for (Type type : types) {
//            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
//        }
        return "design";
    }


//    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
//        return ingredients.stream()
//                .filter(x -> x.getType().equals(type))
//                .collect(Collectors.toList());
//    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute(name="design") Taco design,
                                Errors errors, @ModelAttribute(name="order") Order order) {

        if (errors.hasErrors()) {
            log.info(errors.toString());
            return "design";
        }
        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }

    @ResponseBody
    @GetMapping("/recent")
    public CollectionModel<EntityModel<Taco>> recentTacos() {
        var page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        var tacos = tacoRepository.findAll(page);
        return tacoModelAssembler.toCollectionModel(tacos);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<?> tacoById(@PathVariable("id") long id) {
        Optional<Taco> tacoOptional = tacoRepository.findById(id);
        if (tacoOptional.isPresent()){
            var tacoEntityModel= tacoModelAssembler.toModel(tacoOptional.get());
            return new ResponseEntity<>(tacoEntityModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }
}
