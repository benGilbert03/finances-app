package finances_app.budget;

import finances_app.account.Account;
import finances_app.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping(path = "/budget/account/{accountId}")
    List<Budget> getBudgetsByAccountId(@PathVariable long accountId) {
        Account account = accountRepo.findById(accountId);
        return budgetRepo.findAllByAccount(account);
    }

    @GetMapping(path = "/budget/budget/{budgetId}")
    Budget getBudgetById(@PathVariable long budgetId) {
        return budgetRepo.findById(budgetId);
    }

    @PostMapping(path = "/budget/{frequency}")
    long postBudget(@PathVariable Frequency frequency) {
        Budget b = new Budget(frequency);

        budgetRepo.save(b);
        return b.getId();
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

    @PutMapping(path = "/budget/budget/{id}/{category}/{amount}")
    String modifyBudgetCategoryByBudgetId(@PathVariable long id, @PathVariable String category, @PathVariable double amount) {
        if (budgetRepo.findById(id) == null) {
            return failure;
        } else {
            Budget budget = budgetRepo.findById(id);
            budget.updateBudgetCategory(category, amount);
            budgetRepo.save(budget);
            return success;
        }
    }

    @PutMapping(path = "/budget/spend/{id}/{category}/{amount}")
    String modifySpendCategoryByBudgetId(@PathVariable long id, @PathVariable String category, @PathVariable double amount) {
        if (budgetRepo.findById(id) == null) {
            return failure;
        } else {
            Budget budget = budgetRepo.findById(id);
            budget.updateSpendCategory(category, amount);
            budgetRepo.save(budget);
            return success;
        }
    }

    @PutMapping(path = "budget/name/{id}/{name}")
    String modifyBudgetNameById(@PathVariable long id, @PathVariable String name) {
        if (budgetRepo.findById(id) == null) {
            return failure;
        } else {
            Budget budget = budgetRepo.findById(id);
            budget.setName(name);
            budgetRepo.save(budget);
            return success;
        }
    }

    @PutMapping(path = "/budget/rename-category/{id}/{oldName}/{newName}")
    public String renameCategory(@PathVariable long id, @PathVariable String oldName, @PathVariable String newName) {
        Budget budget = budgetRepo.findById(id);
        if (budget == null) return failure;

        // Transfer Budget Limit
        if (budget.getCategoryBudget().containsKey(oldName)) {
            Double limit = budget.getCategoryBudget().remove(oldName);
            budget.getCategoryBudget().put(newName, limit);
        }

        // Transfer Spent Amount
        if (budget.getCategorySpend().containsKey(oldName)) {
            Double spent = budget.getCategorySpend().remove(oldName);
            budget.getCategorySpend().put(newName, spent);
        }

        budgetRepo.save(budget);
        return success;
    }

    @DeleteMapping(path = "/budget/{id}")
    String deleteBudgetById(@RequestParam long id) {
        Budget budget = budgetRepo.findById(id);
        if (budget == null) {
            return failure;
        } else {
            budgetRepo.delete(budget);
            return success;
        }
    }
}
