package finances_app;

import finances_app.account.Account;
import finances_app.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initAccount(AccountRepository accountRepo) {
        return args -> {
          Account account1 = new Account();
          account1.setUsername("user");
          account1.setPassword("password");
          accountRepo.save(account1);
        };
    }

}