package finances_app.budget;

import finances_app.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findById(long id);
    List<Budget> findAllByAccount(Account account);
}
