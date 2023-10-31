package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.dto.request.OrderAdminDTO;
import start.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findByCustomerId(long CustomerId);

    public List<Order> findByStoreId(long StoreId);

    @Query("SELECT o FROM Order o Join Customer c on o.customer.id = c.id join Store s on o.store.id = s.id")
    public List<Order> findAllOrderWithCustomerAndStoreInformation();

}
