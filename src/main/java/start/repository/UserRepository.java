package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Account;

public interface UserRepository extends JpaRepository<Account, Long> {
    Account findUserByUsername(String username);
}
