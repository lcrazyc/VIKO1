package lt.viko.vvasylieva.springrest.Repositories;

import lt.viko.vvasylieva.springrest.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Account entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
