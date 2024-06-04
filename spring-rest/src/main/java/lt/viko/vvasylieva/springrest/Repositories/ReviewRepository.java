package lt.viko.vvasylieva.springrest.Repositories;

import lt.viko.vvasylieva.springrest.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Review entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
