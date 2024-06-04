package lt.viko.vvasylieva.springrest.Controllers;

import lt.viko.vvasylieva.springrest.Models.Wishlist;
import lt.viko.vvasylieva.springrest.Repositories.WishlistRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class is a REST controller for managing wishlists.
 * It provides endpoints to create, read, update, and delete wishlists.
 */
@RestController
public class WishlistController {
    private final WishlistRepository repository;

    /**
     * Constructor for WishlistController.
     * @param repository the repository used to manage wishlist data.
     */
    WishlistController(WishlistRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all wishlists.
     * @return a collection model of all wishlists wrapped in entity models.
     */
    @GetMapping("/wishlists")
    CollectionModel<EntityModel<Wishlist>> all() {
        List<EntityModel<Wishlist>> wishlists = repository.findAll().stream()
                .map(wishlist -> EntityModel.of(wishlist,
                        linkTo(methodOn(WishlistController.class).one(wishlist.getId())).withSelfRel(),
                        linkTo(methodOn(WishlistController.class).all()).withRel("wishlists")))
                .collect(Collectors.toList());

        return CollectionModel.of(wishlists, linkTo(methodOn(WishlistController.class).all()).withSelfRel());
    }

    /**
     * Creates a new wishlist.
     * @param newWishlist the new wishlist to create.
     * @return the created wishlist.
     */
    @PostMapping("/wishlists")
    Wishlist newWishlist(@RequestBody Wishlist newWishlist) {
        return repository.save(newWishlist);
    }

    /**
     * Retrieves a specific wishlist by its ID.
     * @param id the ID of the wishlist to retrieve.
     * @return the requested wishlist wrapped in an entity model.
     * @throws RuntimeException if the wishlist with the specified ID is not found.
     */
    @GetMapping("/wishlists/{id}")
    EntityModel<Wishlist> one(@PathVariable Integer id) {
        Wishlist wishlist = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist not found: " + id));

        return EntityModel.of(wishlist,
                linkTo(methodOn(WishlistController.class).one(id)).withSelfRel(),
                linkTo(methodOn(WishlistController.class).all()).withRel("wishlists"));
    }

    /**
     * Updates an existing wishlist or creates a new one if it does not exist.
     * @param newWishlist the new wishlist data.
     * @param id the ID of the wishlist to update.
     * @return the updated or created wishlist.
     */
    @PutMapping("/wishlists/{id}")
    Wishlist replaceWishlist(@RequestBody Wishlist newWishlist, @PathVariable Integer id) {
        return repository.findById(id)
                .map(wishlist -> {
                    wishlist.setWishlistName(newWishlist.getWishlistName());
                    wishlist.setGames(newWishlist.getGames());

                    return repository.save(wishlist);
                })
                .orElseGet(() -> {
                    newWishlist.setId(id);
                    return repository.save(newWishlist);
                });
    }

    /**
     * Deletes a wishlist by its ID.
     * @param id the ID of the wishlist to delete.
     */
    @DeleteMapping("/wishlists/{id}")
    void deleteWishlist(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
