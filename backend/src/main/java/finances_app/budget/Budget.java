package finances_app.budget;

import finances_app.account.Account;
import jakarta.persistence.*;

import java.util.HashMap;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Frequency frequency;

    private double totalBudget;

    private double totalSpend;

    private HashMap<String, Double> categoryBudget;
    
    private HashMap<String, Double> categorySpend;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public long getId() {
        return id;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public HashMap<String, Double> getCategoryBudget() {
        return categoryBudget;
    }

    public void setCategoryBudget(HashMap<String, Double> categoryBudget) {
        this.categoryBudget = categoryBudget;
    }

    public HashMap<String, Double> getCategorySpend() {
        return categorySpend;
    }

    public void setCategorySpend(HashMap<String, Double> categorySpend) {
        this.categorySpend = categorySpend;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getTotalBudget() {
        return this.totalBudget;
    }

    public double getTotalSpend() {
        return this.totalSpend;
    }

    public void updateBudgetCategory(String category, double newAmount) {
        Double oldAmount = categoryBudget.put(category, newAmount);
        if (oldAmount.equals(null)) {
            totalBudget += newAmount;
        } else {
            totalBudget = totalBudget - oldAmount.doubleValue() + newAmount;
        }
    }

    public void updateSpendCategory(String category, double newAmount) {
        Double oldAmount = categorySpend.put(category, newAmount);
        if (oldAmount.equals(null)) {
            totalSpend += newAmount;
        } else {
            totalSpend = totalSpend - oldAmount.doubleValue() + newAmount;
        }
    }
}
