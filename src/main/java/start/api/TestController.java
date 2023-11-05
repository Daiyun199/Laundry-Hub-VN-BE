package start.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import start.service.AccountService;
import start.service.CustomerService;
import start.utils.ResponseHandler;

@SecurityRequirement(name = "api")
@RestController
public class TestController {

    @Autowired
    ResponseHandler responseHandler;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;

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

    @PutMapping("/{customerId}/deactive-customer")
    public ResponseEntity deactiveCustomer(@PathVariable("customerId") long customerId ){
        accountService.deactiveCustomer(customerId);
        return responseHandler.response(200, "This customer is deactive now",null);
    }
    @PutMapping("/{customerId}/active-customer")
    public ResponseEntity activeCustomer(@PathVariable("customerId") long customerId ){
        accountService.activeCustomer(customerId);
        return responseHandler.response(200, "This customer is active now",null);
    }

    @PutMapping("/{storeId}/deactive-store")
    public ResponseEntity deactiveStore(@PathVariable("storeId") long storeId ){
        accountService.deactiveStore(storeId);
        return responseHandler.response(200, "This store is deactive now",null);
    }
    @PutMapping("/{storeId}/active-store")
    public ResponseEntity activeStore(@PathVariable("storeId") long storeId ){
        accountService.activeStore(storeId);
        return responseHandler.response(200, "This store is active now",null);
    }
}



