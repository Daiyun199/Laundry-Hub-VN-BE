package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    public OrderDetail findByOrderId(long OrderId);
}
