package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
