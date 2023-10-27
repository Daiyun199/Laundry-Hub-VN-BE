package start.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import start.dto.request.OrderDTO;
import start.entity.Account;
import start.entity.Order;
import start.service.OrderService;
import start.utils.ResponseHandler;

import java.util.List;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "api")
@RequestMapping("api/v1/order")

public class OrderController {
    @Autowired
    private OrderService orderService ;
    @Autowired
    ResponseHandler responseHandler;



    @GetMapping("method = RequestMethod.GET")
    public ResponseEntity getOrderOfCustomer(@RequestParam(value="customerId'")  long customerId){
        
        return responseHandler.response(200,"Orders of Customers are ",orderService.getOrdersOfCustomer(customerId));
    }

    @GetMapping("{StoreId}")
    public ResponseEntity getOrdersOfStore(@PathVariable("StoreId") long storeId){
        return responseHandler.response(200,"Orders of Store are",orderService.getOrdersOfStore(storeId));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping()
    public ResponseEntity addNewOrder(@RequestBody OrderDTO orderDTO){
        return responseHandler.response(200,"Create Order Succesfully",orderService.addOrder(orderDTO));
    }




}
