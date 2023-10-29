package start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import start.entity.Store;

public interface StoreRepository extends JpaRepository<Store,Long> {
    public Store findStoreById(long storeId);
    public Store findStoreByOrdersId(long OrdersId);
}
