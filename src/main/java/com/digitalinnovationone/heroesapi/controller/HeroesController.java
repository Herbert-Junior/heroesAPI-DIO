package com.digitalinnovationone.heroesapi.controller;

import com.digitalinnovationone.heroesapi.document.Heroes;
import com.digitalinnovationone.heroesapi.repository.HeroesRepository;
import com.digitalinnovationone.heroesapi.service.HeroesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static com.digitalinnovationone.heroesapi.constants.HeroesConstant.HERDES_ENDPOINT_LOCAL;


@RestController

public class HeroesController {
    @Autowired
    HeroesService heroesService;

    @Autowired
    HeroesRepository heroesRepository;



    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(HeroesController.class);



    @GetMapping(HERDES_ENDPOINT_LOCAL)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Heroes> getAllItems() {
        return heroesService.findAll();

    }


    @GetMapping(HERDES_ENDPOINT_LOCAL + "/{id}")
    public Mono<ResponseEntity<Heroes>> findByIdHero(@PathVariable String id) {
        log.info("Requesting the hero with id {}", id);
        return heroesService.findById(id)
                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/listTable")
    public Flux<Void> listTable() {
        log.info("Requesting the hero with id {}");
        return heroesService.listTables();
    }

    @PostMapping(HERDES_ENDPOINT_LOCAL)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
        log.info("A new Hero was Created");
        return heroesService.save(heroes);

    }

    @PostMapping("/createTable")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createTable() {
        log.info("A new Hero was Created");
        return heroesService.createTable();

    }


    @DeleteMapping(HERDES_ENDPOINT_LOCAL + "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<HttpStatus> deletebyIDHero(@PathVariable String id) {
        try{
            log.info("Deleting the hero with id {}", id);
            heroesService.deletebyIDHero(id);
            return Mono.just(HttpStatus.OK);

        }catch (Exception e){
            log.error("Erro ao deletar ID inexistente", e);
            return Mono.just(HttpStatus.NOT_FOUND);
        }

    }
}
