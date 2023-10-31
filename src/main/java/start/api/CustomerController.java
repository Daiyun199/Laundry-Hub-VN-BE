package start.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import start.dto.request.CustomerDTO;
import start.service.CustomerService;
import start.service.ServicesService;
import start.utils.ResponseHandler;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "api")
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    ResponseHandler responseHandler;

    @PutMapping()
//    @PreAuthorize("hasAuthority('CUSTOMER')")
    private ResponseEntity updateCustomer(@RequestBody CustomerDTO customerDTO){
      return  responseHandler.response(200, "Your information is changed",customerService.UpdateCustomer(customerDTO));
    }


//    @GetMapping()
//    private ResponseEntity getInfomation(){
//        return responseHandler.response(200, "This is your infomation",customerService.getCustomer());
//    }


    @GetMapping("/admin-function/all-customer")
    private ResponseEntity getAllCustomer() {
        return responseHandler.response(200, "This is all customer", customerService.getAllCustomer());
    }

}
