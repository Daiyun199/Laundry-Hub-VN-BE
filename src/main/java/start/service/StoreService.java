package start.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import start.entity.Store;
import start.repository.StoreRepository;

import java.util.List;

@Service
@AllArgsConstructor

public class StoreService {
    private final StoreRepository storeRepository;

    public List<Store> getAllStore(){
        return storeRepository.findAll();
    }
}
