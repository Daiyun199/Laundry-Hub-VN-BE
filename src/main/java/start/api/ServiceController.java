package start.api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import start.dto.request.ServiceDTO;
import start.entity.Service;
import start.service.ServicesService;
import start.utils.ResponseHandler;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/service")
public class ServiceController {
    @Autowired
    private ServicesService servicesService;
    @Autowired
    ResponseHandler responseHandler;

    @GetMapping("{storeId}")
    public ResponseEntity getAllService(@PathVariable long storeId){
        return responseHandler.response(200,"Load All Services",servicesService.getServices(storeId));
    }


    @PostMapping("{storeId}")
    public ResponseEntity addService(@RequestBody Service service, @PathVariable long storeId){
        return responseHandler.response(200,"Add successfully",servicesService.addService(service,storeId));
    }

    @DeleteMapping("/{StoreId}/{ServiceId}")
    public ResponseEntity deleteService(@PathVariable("StoreId") long StoreId,@PathVariable("ServiceId") long ServiceId){
        servicesService.deleteService(StoreId,ServiceId);
        return responseHandler.response(200,"Delete successfully",null);
    }

    @PutMapping("/{StoreId}/{ServiceId}")
    public ResponseEntity updateService(@PathVariable("StoreId") long StoreId,@PathVariable("ServiceId") long ServiceId ,@RequestBody ServiceDTO request){
        return responseHandler.response(200,"Update successfully",servicesService.updateService(StoreId,ServiceId,request));

    }



}
