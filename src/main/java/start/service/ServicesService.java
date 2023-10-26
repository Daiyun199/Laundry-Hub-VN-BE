package start.service;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import start.dto.request.ServiceDTO;
import start.entity.Store;
import start.exception.exceptions.BadRequest;
import start.repository.ServiceRepository;
import start.repository.StoreRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class ServicesService {
    private final ServiceRepository serviceRepository;
    private final StoreRepository storeRepository;

    public final List<start.entity.Service> getServices(long storeId){
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BadRequest("Can't find this store"));
        return serviceRepository.findServicesByStoreId(storeId);
    }
    public start.entity.Service addService(start.entity.Service service, long storeId){
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BadRequest("Can't find this store"));
        service.setStore(store);
        serviceRepository.save(service);
        return service;
    }
    public void deleteService(long storeId, long serviceId ){
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BadRequest("Can't find this store"));
        start.entity.Service ser = serviceRepository.findById(serviceId).orElseThrow(()-> new BadRequest("Can't find thís Service"));
        ser.setStore(store);
        serviceRepository.delete(ser);
    }
    public start.entity.Service updateService(long storeId, long ServiceId , ServiceDTO request){
        Store store = storeRepository.findStoreById(storeId);
        start.entity.Service ser = serviceRepository.findById(ServiceId).orElseThrow(()-> new BadRequest("Can't find this Service"));
        ser.setName(request.getName());
        ser.setDescription(request.getDescription());
        ser.setFigure(request.getFigure());
        ser.setPrice(request.getPrice());
        ser.setStore(store);
        serviceRepository.save(ser);
        return ser;
    }

}
