package start.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import start.dto.request.OrderCusDTO;
import start.enums.OrderStatusEnum;
import start.service.OrderService;
import start.utils.ResponseHandler;

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
    @GetMapping("Order-of-customer")
    public ResponseEntity getOrderOfCustomer(){
        
        return responseHandler.response(200,"Orders of Customers are ",orderService.getOrdersOfCustomer());
    }
    @PreAuthorize("hasAuthority('STORE') && authentication.principal.store.id.equals(#StoreId)" )
    @GetMapping("all-order-in-store/{StoreId}")
    public ResponseEntity getOrdersOfStore(@PathVariable("StoreId") long StoreId){
        return responseHandler.response(200,"Orders of Store are",orderService.getOrdersOfStore(StoreId));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin-using")
    public ResponseEntity getAllOrder(){
        return  responseHandler.response(200,"This is all order",orderService.getAllOrder());
    }


    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping()
    public ResponseEntity addNewOrder(@RequestBody OrderCusDTO orderDTO){
        return responseHandler.response(200,"Create Order Succesfully",orderService.addOrder(orderDTO));
    }

    @PreAuthorize("hasAuthority('STORE')")
    @PatchMapping("{OrderId}")
    public ResponseEntity updateStatus(@PathVariable("OrderId") long orderId, OrderStatusEnum status){
        return responseHandler.response(200,"Update status successfully",orderService.UpdateStatus(orderId,status));
    }
    @PreAuthorize("hasAuthority('STORE')" )
    @PutMapping("{OrderId}/123213")
    public ResponseEntity updateNumberOfHeight(@PathVariable("OrderId") long orderId, float numberOfHeight){
        return responseHandler.response(200,"Update Height successfully",orderService.updateNumberOfHeight(orderId,numberOfHeight));
    }



}
