package tacos.web;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.stereotype.Component;
import tacos.Taco;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TacoModelAssembler implements RepresentationModelAssembler<Taco, EntityModel<Taco>> {

    @Override
    public EntityModel<Taco> toModel(Taco taco) {
        return EntityModel.of(taco,
                linkTo(methodOn(DesignTacoController.class).tacoById(taco.getId())).withSelfRel(),
                linkTo(methodOn(DesignTacoController.class).recentTacos()).withRel("recent"));
    }

    @Override
    public CollectionModel<EntityModel<Taco>> toCollectionModel(Iterable<? extends Taco> tacos) {
        var modelTacos =  StreamSupport.stream(tacos.spliterator(), false)
                .map(this::toModel).collect(Collectors.toList());

        return CollectionModel.of(modelTacos,
                linkTo(methodOn(DesignTacoController.class).recentTacos()).withSelfRel());
    }
}
