package start.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import start.service.CustomerService;
import start.utils.ResponseHandler;

@SecurityRequirement(name = "api")
@RestController
public class TestController {

    @Autowired
    ResponseHandler responseHandler;
    @Autowired
    private CustomerService customerService;
    @GetMapping("admin-only")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAdmin(){
        return responseHandler.response(200, "Successfully get data!", null);
    }

    @GetMapping("all-user")
    public ResponseEntity get(){
        return responseHandler.response(200, "Successfully get data!", null);
    }

    @GetMapping("information-customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity getCustomer(){
        return responseHandler.response(200," This is your information",customerService.getCustomer());
    }
}
