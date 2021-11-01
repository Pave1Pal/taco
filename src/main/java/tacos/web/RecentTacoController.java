package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tacos.Taco;
import tacos.data.TacoRepository;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin(origins = "*")
@RepositoryRestController
@RequestMapping(path = "/taco", produces = "application/hal+json")
public class RecentTacoController {

    private TacoRepository tacoRepository;
    private TacoModelAssembler tacoModelAssembler;

    @Autowired
    public RecentTacoController(TacoRepository tacoRepository, TacoModelAssembler tacoModelAssembler) {
        this.tacoRepository = tacoRepository;
        this.tacoModelAssembler = tacoModelAssembler;
    }

    @GetMapping(path="/recent", params = {"page", "size"})
    public ResponseEntity<CollectionModel<EntityModel<Taco>>> recentTacos(@RequestParam(name="page", required = false) int page,
                                       @RequestParam(name="size", required = false) int size) {
        var pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var tacos = tacoRepository.findAll(pageRequest).getContent();
        var tacoModels = tacos.stream().map(taco -> tacoModelAssembler.toModel(taco))
                .collect(Collectors.toList());
        var tacosEntity = CollectionModel.of(tacoModels,
                linkTo(methodOn(RecentTacoController.class).recentTacos(page, size)).withRel("recents"));
        return new ResponseEntity(tacosEntity, HttpStatus.OK);
    }
}
