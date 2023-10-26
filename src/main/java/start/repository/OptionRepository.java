package start.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Option;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option,Long> {
    public List<Option> findOptionByServiceId(long serviceId);
}
