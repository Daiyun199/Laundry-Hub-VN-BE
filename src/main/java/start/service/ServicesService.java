package start.service;
import lombok.AllArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.dto.request.ServiceDTO;
import start.entity.Account;
import start.entity.Store;
import start.enums.ServiceStatusEnum;
import start.exception.exceptions.BadRequest;
import start.repository.ServiceRepository;
import start.repository.StoreRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class ServicesService {
    private final ServiceRepository serviceRepository;
    private final StoreRepository storeRepository;

    public final List<start.entity.Service> getServices(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long storeId = account.getStore().getId();
        return serviceRepository.findServicesByStoreId(storeId);
    }
    public start.entity.Service addService(start.entity.Service service){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long storeId = account.getStore().getId();
        service.setStore(account.getStore());
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
    public start.entity.Service updateService( long ServiceId , ServiceDTO request){
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
