package finances_app.user;

import java.util.List;

import finances_app.account.Account;

import jakarta.persistence.*;

@Entity
public class User {
    private String username;
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    /**
     * String is name of the account
     *
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;



}
