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


    @GetMapping(path = "/Account")
    List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @GetMapping(path = "/Account/{username}")
    Account getUserByUsername(@PathVariable String username) {
        Account account = accountRepo.findByUsername(username);
        return account;
    }


    @PostMapping(path = "/Account")
    String postUser(@RequestBody Account account){
        if (accountRepo.findByUsername(account.getUsername()) == null) {
            accountRepo.save(account);
            return success;
        } else {
            return username_exists;
        }
    }

    @DeleteMapping(path = "/Account/{username}")
    String deleteUserByUsername(@PathVariable String username) {
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            return failure;
        } else {
            accountRepo.delete(account);
            return success;
        }
    }
}
