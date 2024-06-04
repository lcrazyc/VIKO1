package lt.viko.vvasylieva.springrest.Repositories;

import lt.viko.vvasylieva.springrest.Models.FavoriteGame;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Favorite Game entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface FavoriteGameRepository extends JpaRepository<FavoriteGame, Integer> {
}
