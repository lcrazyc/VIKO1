package lt.viko.vvasylieva.springrest.Models;

import jakarta.persistence.*;

/**
 * Represents a game entity.
 * This class maps to the 'game' table in the database.
 */
@Entity
@Table(
        name = "game"
)
public class Game {
    /**
     * Unique identifier for the game.
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
     * The title of the game.
     */
    private String title;

    /**
     * The description of the game.
     */
    private String description;

    /**
     * The genre of the game.
     */
    private String genre;

    /**
     * Default constructor.
     */
    public Game() {
    }

    /**
     * Constructor to create a Game object with specified title, description, and genre.
     * @param title The title of the game.
     * @param description The description of the game.
     * @param genre The genre of the game.
     */
    public Game(String title, String description, String genre) {
        this.title = title;
        this.description = description;
        this.genre = genre;
    }

    /**
     * Retrieves the unique identifier of the game.
     * @return The unique identifier of the game.
     */
    public int getId() {
        return Id;
    }

    /**
     * Sets the unique identifier of the game.
     * @param id The unique identifier to be set.
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Retrieves the title of the game.
     * @return The title of the game.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the game.
     * @param title The title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of the game.
     * @return The description of the game.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the game.
     * @param description The description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the genre of the game.
     * @return The genre of the game.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the game.
     * @param genre The genre to be set.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
}

