package finances_app.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findById(long id);
    Users findByUsername(String username);
}
