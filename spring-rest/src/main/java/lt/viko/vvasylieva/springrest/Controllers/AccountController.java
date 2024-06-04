package lt.viko.vvasylieva.springrest.Controllers;

import lt.viko.vvasylieva.springrest.Models.Account;
import lt.viko.vvasylieva.springrest.Repositories.AccountRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing accounts.
 * This controller provides endpoints for CRUD operations on account entities.
 */
@RestController
public class AccountController {
    private final AccountRepository repository;

    /**
     * Constructs an {@code AccountController} with the given repository.
     * @param repository the repository to manage accounts
     */
    AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all accounts.
     * @return a collection model containing entity models of all accounts
     */
    @GetMapping("/accounts")
    CollectionModel<EntityModel<Account>> all() {
        List<EntityModel<Account>> accounts = repository.findAll().stream()
                .map(account -> EntityModel.of(account,
                        linkTo(methodOn(AccountController.class).one(account.getId())).withSelfRel(),
                        linkTo(methodOn(AccountController.class).all()).withRel("accounts")))
                .collect(Collectors.toList());

        return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).all()).withSelfRel());
    }

    /**
     * Creates a new account.
     * @param newAccount the account to be created
     * @return the created account
     */
    @PostMapping("/account")
    Account newAccount(@RequestBody Account newAccount) {
        return repository.save(newAccount);
    }

    /**
     * Retrieves a single account by its ID.
     * @param id the ID of the account to retrieve
     * @return an entity model of the account
     */
    @GetMapping("/accounts/{id}")
    EntityModel<Account> one(@PathVariable Integer id) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));

        return EntityModel.of(account,
                linkTo(methodOn(AccountController.class).one(id)).withSelfRel(),
                linkTo(methodOn(AccountController.class).all()).withRel("accounts"));
    }

    /**
     * Replaces an existing account or creates a new one if the account does not exist.
     * @param newAccount the new account data
     * @param id the ID of the account to replace
     * @return the updated or created account
     */
    @PutMapping("/accounts/{id}")
    Account replaceAccount(@RequestBody Account newAccount, @PathVariable Integer id) {
        return repository.findById(id)
                .map(account -> {
                    account.setUserName(newAccount.getUserName());
                    account.setPassword(newAccount.getPassword());
                    account.setGames(newAccount.getGames());
                    account.setReviews(newAccount.getReviews());
                    account.setWishlist(newAccount.getWishlist());
                    account.setFavoriteGames(newAccount.getFavoriteGames());
                    return repository.save(account);
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    return repository.save(newAccount);
                });
    }

    /**
     * Deletes an account by its ID.
     * @param id the ID of the account to delete
     */
    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}