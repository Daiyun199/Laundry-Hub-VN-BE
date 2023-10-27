package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findByCustomerId(long CustomerId);

    public List<Order> findByStoreId(long StoreId);

}
