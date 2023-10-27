package start.api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import start.dto.request.ServiceDTO;
import start.entity.Service;
import start.service.ServicesService;
import start.utils.ResponseHandler;

import java.util.List;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "api")
@RequestMapping("api/v1/service")
public class ServiceController {
    @Autowired
    private ServicesService servicesService;
    @Autowired
    ResponseHandler responseHandler;

    @GetMapping()
    public ResponseEntity getAllService(){
        return responseHandler.response(200,"Load All Services",servicesService.getServices());
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('STORE') and authentication.principal.store.id.equals(#storeId)" )//authentication.principal.store.id.equals(#storeId)" : Chỉ được post trong chínhc store của mình
    public ResponseEntity addService(@RequestBody Service service){
        return responseHandler.response(200,"Add successfully",servicesService.addService(service));
    }

    @DeleteMapping("{ServiceId}")
    @PreAuthorize("hasAuthority('STORE') and authentication.principal.store.id.equals(#storeId)")
    public ResponseEntity deleteService(@PathVariable("ServiceId") long ServiceId){
        servicesService.deleteService(ServiceId);
        return responseHandler.response(200,"Delete successfully",null);
    }

    @PutMapping("{ServiceId}")
    @PreAuthorize("hasAuthority('STORE') and authentication.principal.store.id.equals(#storeId)")
    public ResponseEntity updateService(@PathVariable("ServiceId") long ServiceId ,@RequestBody ServiceDTO request){
        return responseHandler.response(200,"Update successfully",servicesService.updateService(ServiceId,request));

    }



}
