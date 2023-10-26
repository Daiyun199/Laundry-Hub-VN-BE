package start.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

