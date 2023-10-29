package start.service;
import lombok.AllArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.dto.request.ServiceAndOptionDTO;
import start.dto.request.ServiceDTO;
import start.entity.Account;
import start.entity.Option;
import start.entity.Store;
import start.enums.ServiceStatusEnum;
import start.exception.exceptions.BadRequest;
import start.repository.OptionRepository;
import start.repository.ServiceRepository;
import start.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class ServicesService {
    private final ServiceRepository serviceRepository;
    private final StoreRepository storeRepository;
    private final OptionRepository optionRepository;

    public final List<start.entity.Service> getServices( long storeId){
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BadRequest("This store doesn't exist"));
        return serviceRepository.findServicesByStoreId(storeId);
    }
    public final List<start.entity.Service> getServicesOfStore(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getServices(account.getStore().getId());
    }
    public start.entity.Service addService(ServiceAndOptionDTO serviceAndOptionDTO){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = account.getStore();
        start.entity.Service service = new start.entity.Service();
        service.setName(serviceAndOptionDTO.getName());
        service.setFigure(serviceAndOptionDTO.getFigure());
        service.setDescription(serviceAndOptionDTO.getDescription());
        service.setStatus(ServiceStatusEnum.ACTIVE);
        service.setTitle(serviceAndOptionDTO.getTitle());
        List<Option> options = new ArrayList<>();
        for(Option option : serviceAndOptionDTO.getOptions()){
           Option newOption = new Option();
            newOption.setName(option.getName());
            newOption.setPrice(option.getPrice());
            newOption.setService(service);
            newOption.setDefaultValue(option.isDefaultValue());
            options.add(newOption);

        }
        service.setStore(store);
        service.setOptions(options);
        serviceRepository.save(service);
        return service;
    }
    public void deleteService( long serviceId ){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long storeId = account.getStore().getId();
        start.entity.Service ser = serviceRepository.findById(serviceId).orElseThrow(()-> new BadRequest("Can't find this Service"));
        ser.setStore(account.getStore());
        ser.setStatus(ServiceStatusEnum.DEACTIVE);
        serviceRepository.save(ser);
    }
    public start.entity.Service updateService(long ServiceId , ServiceDTO request){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long storeId = account.getStore().getId();
        start.entity.Service ser = serviceRepository.findById(ServiceId).orElseThrow(()-> new BadRequest("Can't find this Service"));
        ser.setName(request.getName());
        ser.setDescription(request.getDescription());
        ser.setFigure(request.getFigure());
        ser.setTitle(request.getTitle());
        ser.setStatus(request.getStatus());
        ser.setStore(account.getStore());
        serviceRepository.save(ser);
        return ser;
    }

}
