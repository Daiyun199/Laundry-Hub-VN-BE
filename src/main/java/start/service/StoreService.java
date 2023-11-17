package start.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.dto.request.StoreDTO;
import start.entity.Account;
import start.entity.Order;
import start.entity.Store;
import start.enums.RoleEnum;
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
        if(store.getStatus() != StatusEnum.BLOCKED ){
            int countWash =0;
            int countOption =0;
            for (start.entity.Service ser : store.getServices()){
                if(ser.getTitle().equals(TitleEnum.WASH) && ser.getStatus().equals(StatusEnum.ACTIVE)){
                    countWash+=1;
                }else if(ser.getTitle().equals(TitleEnum.OPTION) && ser.getStatus().equals(StatusEnum.ACTIVE) ){
                    countOption+=1;
                }
            }
            if(!(countOption == 0 || countWash == 0)){
                store.setStatus(StatusEnum.ACTIVE);
            }else{
                throw new BadRequest("Please create at least one WASH SERVICE AND OPTION SERVICE before Change your store's status");
            }
        }else{
            throw new BadRequest("Your store is blocked now, please contact admin to unblock your store");
        }
        storeRepository.save(store);
        return store;
    }
    public Store deactivateStore(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = account.getStore();
        store.setStatus(StatusEnum.DEACTIVE);
        storeRepository.save(store);
        return store;
    }
    public void RateStore(long orderId){
//        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = storeRepository.findStoreByOrdersId(orderId);
        float rate2 =0;
        int count =0;
        for(Order order :  store.getOrders()){
            if(order.getRate() > 0) {
                rate2 += order.getRate();
                count +=1;
            }
        }
        store.setRate(rate2/count);
        storeRepository.save(store);
    }
    public Store updateStore(StoreDTO storeDTO){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = account.getStore();
        store.setName(storeDTO.getName());
        store.setAddress(storeDTO.getAddress());
        store.setPhoneNumber(storeDTO.getPhoneNumber());
        store.setCoverPhoto(storeDTO.getCoverPhoto());
        store.setDescription(storeDTO.getDescription());
        return storeRepository.save(store);
    }
    public Store getInformationStore(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return account.getStore();
    }
    public int countStore(){
        return storeRepository.findAll().size();
    }
}
