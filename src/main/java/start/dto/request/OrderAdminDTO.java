package start.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import start.entity.Option;
import start.entity.OrderDetail;
import start.entity.Service;
import start.enums.OrderStatusEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class OrderAdminDTO {
    private long id;
    private String address;
    private float numberOfHeightSto;
    private OrderStatusEnum orderStatus;
    private float rate;
    private float totalPrice;
    private String customerNumber;
    private String customerName;
    private String storeName;
    private String DayCreateOrder;
    private List<OrderDetail> orderDetails;

}
