package lt.viko.vvasylieva.springrest.Repositories;

import lt.viko.vvasylieva.springrest.Models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Game entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface GameRepository extends JpaRepository<Game, Integer> {
}
