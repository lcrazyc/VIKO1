package lt.viko.vvasylieva.springrest.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a wishlist entity.
 * This class maps to the 'wishlist' table in the database.
 */
@Entity
@Table(
        name = "wishlist"
)
public class Wishlist {
    /**
     * Unique identifier for the wishlist.
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
     * The name of the wishlist.
     */
    private String wishlistName;

    /**
     * The list of games in the wishlist.
     * Represents a one-to-many relationship with the Game entity.
     * Cascade type is set to ALL, meaning all operations are cascaded to the associated Game entities.
     */
    @OneToMany(
            targetEntity = Game.class,
            cascade = {CascadeType.ALL}
    )
    private List<Game> games = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Wishlist() {
    }

    /**
     * Constructor to create a Wishlist object with specified wishlist name and games.
     * @param wishlistName The name of the wishlist.
     * @param games The list of games in the wishlist.
     */
    public Wishlist(String wishlistName, List<Game> games) {
        this.wishlistName = wishlistName;
        this.games = games;
    }

    /**
     * Retrieves the unique identifier of the wishlist.
     * @return The unique identifier of the wishlist.
     */
    public int getId() {
        return Id;
    }

    /**
     * Sets the unique identifier of the wishlist.
     * @param id The unique identifier to be set.
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Retrieves the name of the wishlist.
     * @return The name of the wishlist.
     */
    public String getWishlistName() {
        return wishlistName;
    }

    /**
     * Sets the name of the wishlist.
     * @param wishlistName The name to be set.
     */
    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    /**
     * Retrieves the list of games in the wishlist.
     * @return The list of games in the wishlist.
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Sets the list of games in the wishlist.
     * @param games The list of games to be set.
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }
}
