package finances_app.account;

import finances_app.user.User;
import jakarta.persistence.*;

/**
 * @author Ben Gilbert
 */
@Entity
public class Account {
    private String name;
    private double balance;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    /**
     * Constructor that initializes an Account
     */
    public Account() {
        this.name = "";
        this.balance = 0.0;
    }
}
