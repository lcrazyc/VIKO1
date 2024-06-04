package lt.viko.vvasylieva.springrest.Models;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents an account entity.
 * This class maps to the 'account' table in the database.
 */
@Entity
@Table(
        name = "account"
)
public class Account {
    /**
     * Unique identifier for the account.
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
     * The username of the account.
     */
    private String userName;

    /**
     * The password of the account.
     */
    private String password;

    /**
     * The list of games associated with this account.
     * Represents a one-to-many relationship with the Game entity.
     * Cascade type is set to ALL, meaning all operations are cascaded to the associated Game entities.
     */
    @OneToMany(
            targetEntity = Game.class,
            cascade = {CascadeType.ALL}
    )
    private List<Game> games;

    /**
     * The list of reviews written by this account.
     * Represents a one-to-many relationship with the Review entity.
     * Cascade type is set to ALL, meaning all operations are cascaded to the associated Review entities.
     */
    @OneToMany(
            targetEntity = Review.class,
            cascade = {CascadeType.ALL}
    )
    private List<Review> reviews;

    /**
     * The wishlist associated with this account.
     * Represents a one-to-one relationship with the Wishlist entity.
     * Cascade type is set to ALL, meaning all operations are cascaded to the associated Wishlist entity.
     */
    @OneToOne(
            targetEntity = Wishlist.class,
            cascade = {CascadeType.ALL}
    )
    private Wishlist wishlist;

    /**
     * The list of favorite games associated with this account.
     * Represents a one-to-many relationship with the FavoriteGame entity.
     * Cascade type is set to ALL, meaning all operations are cascaded to the associated FavoriteGame entities.
     */
    @OneToMany(
            targetEntity = FavoriteGame.class,
            cascade = {CascadeType.ALL}
    )
    private List<FavoriteGame> favoriteGames;

    /**
     * Default constructor.
     */
    public Account() {
    }

    /**
     * Constructor to create an Account object with specified username, password, games, reviews, wishlist, and favorite games.
     * @param userName The username of the account.
     * @param password The password of the account.
     * @param games The list of games associated with this account.
     * @param reviews The list of reviews written by this account.
     * @param wishlist The wishlist associated with this account.
     * @param favoriteGames The list of favorite games associated with this account.
     */
    public Account(String userName, String password, List<Game> games, List<Review> reviews, Wishlist wishlist, List<FavoriteGame> favoriteGames) {
        this.userName = userName;
        this.password = password;
        this.games = games;
        this.reviews = reviews;
        this.wishlist = wishlist;
        this.favoriteGames = favoriteGames;
    }

    /**
     * Returns a string representation of the account, displaying username and password.
     * @return A string representation of the account.
     */
    public String toString() {
        return String.format("\t\tUserName: %s\n\t\tPassword: %s\n", this.userName, this.password);
    }

    /**
     * Retrieves the unique identifier of the account.
     * @return The unique identifier of the account.
     */
    public int getId() {
        return this.Id;
    }

    /**
     * Sets the unique identifier of the account.
     * @param id The unique identifier to be set.
     */
    public void setId(int id) {
        this.Id = id;
    }

    /**
     * Retrieves the username of the account.
     * @return The username of the account.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Sets the username of the account.
     * @param userName The username to be set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the password of the account.
     * @return The password of the account.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password of the account.
     * @param password The password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the list of games associated with this account.
     * @return The list of games associated with this account.
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Sets the list of games associated with this account.
     * @param games The list of games to be set.
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    /**
     * Retrieves the list of reviews written by this account.
     * @return The list of reviews written by this account.
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews written by this account.
     * @param reviews The list of reviews to be set.
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Retrieves the wishlist associated with this account.
     * @return The wishlist associated with this account.
     */
    public Wishlist getWishlist() {
        return wishlist;
    }

    /**
     * Sets the wishlist associated with this account.
     * @param wishlist The wishlist to be associated with this account.
     */
    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    /**
     * Retrieves the list of favorite games associated with this account.
     * @return The list of favorite games associated with this account.
     */
    public List<FavoriteGame> getFavoriteGames() {
        return favoriteGames;
    }

    /**
     * Sets the list of favorite games associated with this account.
     * @param favoriteGames The list of favorite games to be set.
     */
    public void setFavoriteGames(List<FavoriteGame> favoriteGames) {
        this.favoriteGames = favoriteGames;
    }
}
