package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import start.entity.Service;
import start.enums.TitleEnum;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {
    public List<Service> findServicesByStoreId(long storeId);
    public Service findServiceByOptionsId(long optionId);

//    @Query( "select s from Order  o join OrderDetail od on o.id = od.order.id join Service s on od.service.id = s.id where s.title = 'WASH' and o.id like %?1% ")
//    @Query("SELECT s FROM Service s JOIN OrderDetail od JOIN Order o WHERE o.id = ?1 and s.title = 'WASH'")
    @Query( "select s from Order  o join OrderDetail od on o.id = od.order.id join Service s on od.service.id = s.id where s.title = 'WASH' and o.id = ?1 ")
    public Service findServiceByOrderIdAndTitle(Long orderId);
}
