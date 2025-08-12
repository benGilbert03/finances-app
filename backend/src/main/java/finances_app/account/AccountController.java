package finances_app.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountRepository accountRepo;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    private String username_exists = "{\"message\":\"username already in use\"}";


    @GetMapping(path = "/account")
    List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @GetMapping(path = "/account/username/{username}")
    Account getAccountByUsername(@PathVariable String username) {
        Account account = accountRepo.findByUsername(username);
        return account;
    }

    @GetMapping(path = "/account/id/{id}")
    Account getAccountById(@PathVariable long id) {
        Account account = accountRepo.findById(id);
        return account;
    }


    @PostMapping(path = "/account")
    long postAccount(@RequestBody Account account){
        if (accountRepo.findByUsername(account.getUsername()) == null) {
            accountRepo.save(account);
            return account.getId();
        } else {
            return -1;
        }
    }

    @DeleteMapping(path = "/account/{username}")
    String deleteAccountByUsername(@PathVariable String username) {
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            return failure;
        } else {
            accountRepo.delete(account);
            return success;
        }
    }
}
