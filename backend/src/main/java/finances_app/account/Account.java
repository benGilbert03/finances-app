package finances_app.account;

import finances_app.users.Users;
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
    @JoinColumn(name = "users_id")
    private Users users;


    /**
     * Constructor that initializes an Account
     */
    public Account() {
        this.name = "";
        this.balance = 0.0;
    }
}
