package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Service;

public interface ServiceRepository extends JpaRepository<Service,Long> {
}
