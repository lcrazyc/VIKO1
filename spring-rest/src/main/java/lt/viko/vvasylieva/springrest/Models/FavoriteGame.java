package lt.viko.vvasylieva.springrest.Models;

import jakarta.persistence.*;

/**
 * Represents a favorite game entity.
 * This class maps to the 'favorite_game' table in the database.
 */
@Entity
@Table(
        name = "favorite_game"
)
public class FavoriteGame {
    /**
     * Unique identifier for the favorite game.
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
     * The game associated with this favorite.
     * Represents a one-to-one relationship with the Game entity.
     * Cascade type is set to ALL, meaning all operations are cascaded to the associated Game entity.
     */
    @OneToOne(
            targetEntity = Game.class,
            cascade = {CascadeType.ALL}
    )
    private Game game;

    /**
     * Default constructor.
     */
    public FavoriteGame() {
    }

    /**
     * Constructor to create a FavoriteGame object with a specified game.
     * @param game The game to be set as favorite.
     */
    public FavoriteGame(Game game) {
        this.game = game;
    }

    /**
     * Retrieves the unique identifier of the favorite game.
     * @return The unique identifier of the favorite game.
     */
    public int getId() {
        return Id;
    }

    /**
     * Sets the unique identifier of the favorite game.
     * @param id The unique identifier to be set.
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Retrieves the game associated with this favorite.
     * @return The game associated with this favorite.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the game associated with this favorite.
     * @param game The game to be associated with this favorite.
     */
    public void setGame(Game game) {
        this.game = game;
    }
}
