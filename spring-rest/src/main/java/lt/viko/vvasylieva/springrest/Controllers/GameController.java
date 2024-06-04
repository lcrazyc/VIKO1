package lt.viko.vvasylieva.springrest.Controllers;

import lt.viko.vvasylieva.springrest.Models.Game;
import lt.viko.vvasylieva.springrest.Repositories.GameRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing games.
 */
@RestController
public class GameController {
    private final GameRepository repository;

    /**
     * Constructs a new GameController with the specified GameRepository.
     * @param repository the GameRepository to be used for data access
     */
    GameController(GameRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all games from the repository.
     * @return a CollectionModel containing EntityModels of all games with self and collection links
     */
    @GetMapping("/games")
    CollectionModel<EntityModel<Game>> all() {
        List<EntityModel<Game>> games = repository.findAll().stream()
                .map(game -> EntityModel.of(game,
                        linkTo(methodOn(GameController.class).one(game.getId())).withSelfRel(),
                        linkTo(methodOn(GameController.class).all()).withRel("games")))
                .collect(Collectors.toList());

        return CollectionModel.of(games, linkTo(methodOn(GameController.class).all()).withSelfRel());
    }

    /**
     * Adds a new game to the repository.
     * @param newGame the new Game object to be added
     * @return the saved Game object
     */
    @PostMapping("/game")
    Game newGame(@RequestBody Game newGame) {
        return repository.save(newGame);
    }

    /**
     * Retrieves a single game by its ID.
     * @param id the ID of the game to retrieve
     * @return an EntityModel of the retrieved game with self and collection links
     * @throws RuntimeException if the game is not found
     */
    @GetMapping("/games/{id}")
    EntityModel<Game> one(@PathVariable Integer id) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found: " + id));

        return EntityModel.of(game,
                linkTo(methodOn(GameController.class).one(id)).withSelfRel(),
                linkTo(methodOn(GameController.class).all()).withRel("games"));
    }

    /**
     * Replaces an existing game or adds a new game if the specified ID does not exist.
     * @param newGame the new Game object with updated information
     * @param id      the ID of the game to replace
     * @return the saved Game object, either updated or newly created
     */
    @PutMapping("/games/{id}")
    Game replaceGame(@RequestBody Game newGame, @PathVariable Integer id) {
        return repository.findById(id)
                .map(game -> {
                    game.setTitle(newGame.getTitle());
                    game.setDescription(newGame.getDescription());
                    game.setGenre(newGame.getGenre());

                    return repository.save(game);
                })
                .orElseGet(() -> {
                    newGame.setId(id);
                    return repository.save(newGame);
                });
    }

    /**
     * Deletes a game by its ID.
     * @param id the ID of the game to delete
     */
    @DeleteMapping("/games/{id}")
    void deleteGame(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
