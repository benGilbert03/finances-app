package finances_app.budget;

import finances_app.account.Account;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    private String name;

    private Frequency frequency;

    private double totalBudget;

    private double totalSpend;

    @ElementCollection
    @CollectionTable(name = "budget_category_limits", joinColumns = @JoinColumn(name = "budget_id"))
    @MapKeyColumn(name = "category_name")
    @Column(name = "limit_amount")
    private Map<String, Double> categoryBudget = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "budget_category_spend", joinColumns = @JoinColumn(name = "budget_id"))
    @MapKeyColumn(name = "category_name")
    @Column(name = "spend_amount")
    private Map<String, Double> categorySpend = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Budget() {
    }

    public Budget(Frequency frequency) {
        this.frequency = frequency;
        name = "";
        totalBudget = 0.0;
        totalSpend = 0.0;
        this.categoryBudget = new HashMap<>();
        this.categorySpend = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Map<String, Double> getCategoryBudget() {
        return categoryBudget;
    }

    public void setCategoryBudget(Map<String, Double> categoryBudget) {
        this.categoryBudget = categoryBudget;
    }

    public Map<String, Double> getCategorySpend() {
        return categorySpend;
    }

    public void setCategorySpend(Map<String, Double> categorySpend) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateBudgetCategory(String category, double newAmount) {
        Double oldAmount = categoryBudget.put(category, newAmount);
        if (oldAmount == null) {
            totalBudget += newAmount;
        } else {
            totalBudget = totalBudget - oldAmount.doubleValue() + newAmount;
        }
    }

    public void updateSpendCategory(String category, double newAmount) {
        Double oldAmount = categorySpend.put(category, newAmount);
        if (oldAmount == null) {
            totalSpend += newAmount;
        } else {
            totalSpend = totalSpend - oldAmount.doubleValue() + newAmount;
        }
    }
}
