package start.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    private ServicesService accountService;
    @Autowired
    ResponseHandler responseHandler;

//    @PatchMapping
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    private ResponseEntity UpdateCustomer(@RequestBody CustomerDTO customerDTO){
//      return  responseHandler.response(200, "Your information is changed",customerService.UpdateCustomer(customerDTO));
//    }


//    @GetMapping()
//    private ResponseEntity getInfomation(){
//        System.out.println("customerService: "+accountService);
//        return responseHandler.response(200, "This is your infomation",accountService.getServices());
//    }
}
