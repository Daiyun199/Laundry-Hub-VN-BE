package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.entity.Account;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<Account, Long> {
    Account findUserByUsername(String username);
    List<Account> findAllByDateCreateBetween(Date start, Date end);
    @Query("SELECT a FROM Account a WHERE YEAR(a.dateCreate) = ?1 AND MONTH(a.dateCreate) = ?2")
    List<Account> findByCreatedAtMonthYear(int year, int month);}
