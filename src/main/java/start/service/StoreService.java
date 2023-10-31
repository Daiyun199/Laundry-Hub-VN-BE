package start.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.entity.Account;
import start.entity.Store;
import start.enums.StatusEnum;
import start.enums.TitleEnum;
import start.exception.exceptions.BadRequest;
import start.repository.StoreRepository;
import start.utils.ResponseHandler;

import java.util.List;

@Service
@AllArgsConstructor

public class StoreService {
    private final StoreRepository storeRepository;
    @Autowired
    ResponseHandler responseHandler;


    public List<Store> getAllStore(){
        return storeRepository.findAll();
    }

    public Store updateStatus(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = account.getStore();
        int countWash =0;
        int countOption =0;
        for (start.entity.Service ser : store.getServices()){
            if(ser.getTitle().equals(TitleEnum.WASH)){
                countWash+=1;
            }else if(ser.getTitle().equals(TitleEnum.OPTION)){
                countOption+=1;
            }
        }
        if(!((countWash == 0 && countOption == 0) || (countWash == 1 && countOption == 0) || (countWash == 0 && countOption == 1))){
            store.setStatus(StatusEnum.ACTIVE);
        }else{
          throw new BadRequest("Please choose at least one WASH SERVICE AND OPTION SERVICE before Change your store's status");
        }
        storeRepository.save(store);
        return store;
    }


}
