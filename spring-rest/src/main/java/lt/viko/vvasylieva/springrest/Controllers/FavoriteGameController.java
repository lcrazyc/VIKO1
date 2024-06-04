package lt.viko.vvasylieva.springrest.Controllers;

import lt.viko.vvasylieva.springrest.Models.FavoriteGame;
import lt.viko.vvasylieva.springrest.Repositories.FavoriteGameRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing favorite games.
 * This controller provides endpoints for CRUD operations on favorite game entities.
 */
@RestController
public class FavoriteGameController {
    private final FavoriteGameRepository repository;

    /**
     * Constructs a {@code FavoriteGameController} with the given repository.
     * @param repository the repository to manage favorite games
     */
    FavoriteGameController(FavoriteGameRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all favorite games.
     * @return a collection model containing entity models of all favorite games
     */
    @GetMapping("/favoriteGames")
    CollectionModel<EntityModel<FavoriteGame>> all() {
        List<EntityModel<FavoriteGame>> favoriteGames = repository.findAll().stream()
                .map(favoriteGame -> EntityModel.of(favoriteGame,
                        linkTo(methodOn(FavoriteGameController.class).one(favoriteGame.getId())).withSelfRel(),
                        linkTo(methodOn(FavoriteGameController.class).all()).withRel("favoriteGames")))
                .collect(Collectors.toList());

        return CollectionModel.of(favoriteGames, linkTo(methodOn(FavoriteGameController.class).all()).withSelfRel());
    }

    /**
     * Creates a new favorite game.
     * @param newFavGame the favorite game to be created
     * @return the created favorite game
     */
    @PostMapping("/favoriteGame")
    FavoriteGame newFavGame(@RequestBody FavoriteGame newFavGame) {
        return repository.save(newFavGame);
    }

    /**
     * Retrieves a single favorite game by its ID.
     * @param id the ID of the favorite game to retrieve
     * @return an entity model of the favorite game
     */
    @GetMapping("/favoriteGames/{id}")
    EntityModel<FavoriteGame> one(@PathVariable Integer id) {
        FavoriteGame favoriteGame = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite Game not found: " + id));

        return EntityModel.of(favoriteGame,
                linkTo(methodOn(FavoriteGameController.class).one(id)).withSelfRel(),
                linkTo(methodOn(FavoriteGameController.class).all()).withRel("favoriteGames"));
    }

    /**
     * Replaces an existing favorite game or creates a new one if the game does not exist.
     * @param newFavGame the new favorite game data
     * @param id the ID of the favorite game to replace
     * @return the updated or created favorite game
     */
    @PutMapping("/favoriteGames/{id}")
    FavoriteGame replaceFavGame(@RequestBody FavoriteGame newFavGame, @PathVariable Integer id) {
        return repository.findById(id)
                .map(favoriteGame -> {
                    favoriteGame.setGame(newFavGame.getGame());
                    return repository.save(favoriteGame);
                })
                .orElseGet(() -> {
                    newFavGame.setId(id);
                    return repository.save(newFavGame);
                });
    }

    /**
     * Deletes a favorite game by its ID.
     * @param id the ID of the favorite game to delete
     */
    @DeleteMapping("/favoriteGames/{id}")
    void deleteFavGame(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}