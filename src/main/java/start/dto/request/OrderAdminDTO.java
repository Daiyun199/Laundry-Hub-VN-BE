package start.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import start.enums.OrderStatusEnum;

import javax.persistence.*;

@Data
public class OrderAdminDTO {
    private long id;
    private String address;
    private float numberOfHeightCus;
    private float numberOfHeightSto;
    private OrderStatusEnum orderStatus;
    private float rate;
    private float totalPrice;
    private String customerNumber;
    private String customerName;
    private String storeName;

}
