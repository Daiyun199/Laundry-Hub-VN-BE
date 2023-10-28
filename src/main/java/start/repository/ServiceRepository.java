package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Service;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {
    public List<Service> findServicesByStoreId(long storeId);
    public Service findServiceByOptionsId(long optionId);
}
