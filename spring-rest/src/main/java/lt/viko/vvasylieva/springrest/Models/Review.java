package lt.viko.vvasylieva.springrest.Models;

import jakarta.persistence.*;

/**
 * Represents a review entity.
 * This class maps to the 'review' table in the database.
 */
@Entity
@Table(
        name = "review"
)
public class Review {
    /**
     * Unique identifier for the review.
     */
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "id"
    )
    private int Id;

    /**
     * The game associated with this review.
     * Represents a one-to-one relationship with the Game entity.
     * Cascade type is set to ALL, meaning all operations are cascaded to the associated Game entity.
     */
    @OneToOne(
            targetEntity = Game.class,
            cascade = {CascadeType.ALL}
    )
    private Game game;

    /**
     * The text content of the review.
     */
    private String reviewText;

    /**
     * The rating given in the review.
     */
    private double rating;

    /**
     * Default constructor.
     */
    public Review() {
    }

    /**
     * Constructor to create a Review object with specified game, review text, and rating.
     * @param game The game associated with the review.
     * @param reviewText The text content of the review.
     * @param rating The rating given in the review.
     */
    public Review(Game game, String reviewText, double rating) {
        this.game = game;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    /**
     * Retrieves the unique identifier of the review.
     * @return The unique identifier of the review.
     */
    public int getId() {
        return Id;
    }

    /**
     * Sets the unique identifier of the review.
     * @param id The unique identifier to be set.
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Retrieves the game associated with the review.
     * @return The game associated with the review.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the game associated with the review.
     * @param game The game to be associated with the review.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Retrieves the text content of the review.
     * @return The text content of the review.
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Sets the text content of the review.
     * @param reviewText The text content to be set.
     */
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    /**
     * Retrieves the rating given in the review.
     * @return The rating given in the review.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the rating given in the review.
     * @param rating The rating to be set.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }
}
