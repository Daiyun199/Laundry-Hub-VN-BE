package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.dto.request.OrderAdminDTO;
import start.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findByCustomerId(long CustomerId);

    public List<Order> findByStoreId(long StoreId);

//    @Query("SELECT o.id, o.address,o.numberOfHeightCus , o.numberOfHeightSto, o.orderStatus,o.rate,o.totalPrice,o.customerNumber,c.name, s.name FROM Order o Join Customer c on o.customer.id = c.id join Store s on o.store.id = s.id")
//    public List<OrderAdminDTO> findAllOrderWithCustomerAndStoreInformation();

}
