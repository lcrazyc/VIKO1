package lt.viko.vvasylieva.springrest.Controllers;

import lt.viko.vvasylieva.springrest.Models.Review;
import lt.viko.vvasylieva.springrest.Repositories.ReviewRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing reviews.
 */
@RestController
public class ReviewController {
    private final ReviewRepository repository;

    /**
     * Constructs a new ReviewController with the specified repository.
     * @param repository the review repository
     */
    ReviewController(ReviewRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all reviews.
     * @return a CollectionModel containing EntityModels of all reviews
     */
    @GetMapping("/reviews")
    CollectionModel<EntityModel<Review>> all() {
        List<EntityModel<Review>> reviews = repository.findAll().stream()
                .map(review -> EntityModel.of(review,
                        linkTo(methodOn(ReviewController.class).one(review.getId())).withSelfRel(),
                        linkTo(methodOn(ReviewController.class).all()).withRel("reviews")))
                .collect(Collectors.toList());

        return CollectionModel.of(reviews, linkTo(methodOn(ReviewController.class).all()).withSelfRel());
    }

    /**
     * Creates a new review.
     * @param newReview the review to create
     * @return the created review
     */
    @PostMapping("/review")
    Review newReview(@RequestBody Review newReview) {
        return repository.save(newReview);
    }

    /**
     * Retrieves a single review by its ID.
     * @param id the ID of the review to retrieve
     * @return an EntityModel of the retrieved review
     * @throws RuntimeException if the review is not found
     */
    @GetMapping("/reviews/{id}")
    EntityModel<Review> one(@PathVariable Integer id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found: " + id));

        return EntityModel.of(review,
                linkTo(methodOn(ReviewController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ReviewController.class).all()).withRel("reviews"));
    }

    /**
     * Replaces an existing review with a new review.
     * @param newReview the new review data
     * @param id        the ID of the review to replace
     * @return the updated review
     */
    @PutMapping("/reviews/{id}")
    Review replaceReview(@RequestBody Review newReview, @PathVariable Integer id) {
        return repository.findById(id)
                .map(review -> {
                    review.setReviewText(newReview.getReviewText());
                    review.setGame(newReview.getGame());
                    review.setRating(newReview.getRating());
                    return repository.save(review);
                })
                .orElseGet(() -> {
                    newReview.setId(id);
                    return repository.save(newReview);
                });
    }

    /**
     * Deletes a review by its ID.
     * @param id the ID of the review to delete
     */
    @DeleteMapping("/reviews/{id}")
    void deleteReview(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
