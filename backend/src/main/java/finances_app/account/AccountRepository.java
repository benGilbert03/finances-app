package finances_app.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(long id);
    Account findByUsername(String username);
}
