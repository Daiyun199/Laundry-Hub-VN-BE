package start.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Option;

public interface OptionRepository extends JpaRepository<Option,Long> {
}
