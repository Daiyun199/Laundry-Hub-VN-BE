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
import start.enums.OrderStatusEnum;
import start.service.OrderService;
import start.utils.ResponseHandler;

import java.util.List;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "api")// Sử dụng yêu cầu này giúp
// đảm bảo rằng chỉ các đối tượng được ủy quyền mới có thể truy cập
// và sử dụng các API của hệ thống.
@RequestMapping("api/v1/order")

public class OrderController {
    @Autowired
    private OrderService orderService ;
    @Autowired
    ResponseHandler responseHandler;


    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping()
    public ResponseEntity getOrderOfCustomer(){
        
        return responseHandler.response(200,"Orders of Customers are ",orderService.getOrdersOfCustomer());
    }
    @PreAuthorize("hasAuthority('STORE')")
    @GetMapping("{StoreId}")
    public ResponseEntity getOrdersOfStore(@PathVariable("{StoreId}") long StoreId){
        return responseHandler.response(200,"Orders of Store are",orderService.getOrdersOfStore(StoreId));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping()
    public ResponseEntity addNewOrder(@RequestBody OrderDTO orderDTO){
        return responseHandler.response(200,"Create Order Succesfully",orderService.addOrder(orderDTO));
    }

    @PreAuthorize("hasAuthority('STORE')")
    @PatchMapping("{OrderId}")
    public ResponseEntity updateStatus(@PathVariable("OrderId") long orderId, OrderStatusEnum status){
        return responseHandler.response(200,"Update status successfully",orderService.UpdateStatus(orderId,status));
    }

}
