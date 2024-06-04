package lt.viko.vvasylieva.springrest.Repositories;

import lt.viko.vvasylieva.springrest.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Wishlist entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
}
