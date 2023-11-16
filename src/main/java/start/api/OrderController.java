package start.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import start.dto.request.OrderCusDTO;
import start.enums.OrderStatusEnum;
import start.exception.exceptions.BadRequest;
import start.service.AccountService;
import start.service.OrderService;
import start.service.StoreService;
import start.utils.ResponseHandler;

import java.util.Date;

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

    @Autowired
    private StoreService storeService;
    @Autowired
    private AccountService accountService;


    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("Order-of-customer")
    public ResponseEntity getOrderOfCustomer(){
        
        return responseHandler.response(200,"Orders of Customers are ",orderService.getOrdersOfCustomer());
    }
    @PreAuthorize("hasAuthority('STORE') " )
    @GetMapping("all-order-in-store")
    public ResponseEntity getOrdersOfStore( ){
        return responseHandler.response(200,"Orders of Store are",orderService.getOrdersOfStore());
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
    @PutMapping("{OrderId}/update-status")
    public ResponseEntity updateStatus(@PathVariable("OrderId") long orderId, OrderStatusEnum status,@RequestParam(required = false) String feedback){
        if(status != OrderStatusEnum.STORE_REJECT && feedback != null) {
            throw new BadRequest("Feedback should only be provided for orders with status STORE_REJECT.");
        }
        if(status == OrderStatusEnum.DONE){
            accountService.blockAccount(orderId);
        }
        return responseHandler.response(200,"Update status successfully",orderService.UpdateStatus(orderId,status,feedback));
    }
    @PreAuthorize("hasAuthority('STORE')" )
    @PutMapping("{OrderId}/update-number-of-height")
    public ResponseEntity updateNumberOfHeight(@PathVariable("OrderId") long orderId, float numberOfHeight ,   @RequestParam("dateDelivery") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Date dateDelivery){
        return responseHandler.response(200,"Update Height successfully",orderService.updateNumberOfHeight(orderId,numberOfHeight,dateDelivery));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')" )
    @PutMapping("{OrderId}/rate-order")
    public ResponseEntity rateOrder(@PathVariable("OrderId") long orderId , float rate, String feedback ){
        orderService.RateOrder(orderId,rate,feedback);
        storeService.RateStore(orderId);
        return responseHandler.response(200,"Thank you for your review",null);
    }
    @PreAuthorize("hasAuthority('ADMIN')" )
    @GetMapping("count-order")
    public ResponseEntity countOrderOnProcess(){
        return responseHandler.response(200,"",orderService.countOrderOnProcess());
    }

    @GetMapping("all-order-in-store/{orderId}")
    public ResponseEntity getOrderbyId(@PathVariable("orderId") long orderId){
        return  responseHandler.response(200,"This is all information of order you want", orderService.getOrderbyId(orderId));
    }
    @GetMapping("view-by-customer/{orderId}")
    public ResponseEntity viewOrderByCustomer(@PathVariable("orderId") long orderId){
        return  responseHandler.response(200,"This is all information of order you want", orderService.viewOrderByCustomer(orderId));
    }


}
