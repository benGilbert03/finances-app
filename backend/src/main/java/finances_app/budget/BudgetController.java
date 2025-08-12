package finances_app.budget;

import finances_app.account.Account;
import finances_app.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BudgetController {
    @Autowired
    BudgetRepository budgetRepo;
    @Autowired
    AccountRepository accountRepo;
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/budget")
    List<Budget> getAllBudgets() {
        return budgetRepo.findAll();
    }

    @GetMapping(path = "/budget/{accountId}")
    List<Budget> getBudgetsByAccountId(@PathVariable long accountId) {
        Account account = accountRepo.findById(accountId);
        return budgetRepo.findAllByAccount(account);
    }

    @PostMapping(path = "/budget")
    long postBudget(@RequestBody Budget budget) {
        budgetRepo.save(budget);
        return budget.getId();
    }

    @PutMapping(path = "/budget/account")
    String assignBudgetToAccount(@RequestParam long budgetId, @RequestParam long accountId) {
        Budget budget = budgetRepo.findById(budgetId);
        Account account = accountRepo.findById(accountId);
        if (budget == null || account == null) {
            return failure;
        } else {
            budget.setAccount(account);
            budgetRepo.save(budget);
            return success;
        }
    }




//    @PutMapping(path = "/budget/{id}")
//    String putBudgetById(@PathVariable long id, @RequestBody Budget newBudget) {
//        Budget oldBudget = budgetRepo.findById(id);
//        if (oldBudget == null) {
//            return failure;
//        } else {
//            if (newBudget.getCategoryBudget() != null) {
//                oldBudget.setCategoryBudget(newBudget.getCategoryBudget());
//            }
//        }
//    }
}
