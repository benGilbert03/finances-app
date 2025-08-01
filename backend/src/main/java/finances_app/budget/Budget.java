package finances_app.budget;

import finances_app.account.Account;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.HashMap;

public class Budget {
    private Frequency frequency;

    private double totalBudget;

    private HashMap<String, Double> categoryBudget;

    private HashMap<String, Double> categorySpending;

//    @ManyToOne(mappedBy = "budget")
    private Account account;

}
