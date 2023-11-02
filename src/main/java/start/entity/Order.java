package start.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import start.enums.OrderStatusEnum;
import start.enums.RoleEnum;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @DecimalMin(value = "0.00", inclusive = false)
    @DecimalMax(value = "5.00", inclusive = false)
    private float rate;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;
    @Column(columnDefinition = "nvarchar(max)")
    private String address;
    private float totalPrice;
    private float numberOfHeightCus;
    private float numberOfHeightSto;
    private float totalPriceStoUp;
    private String customerNumber;
    private String  DayCreateOrder;


    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="store_id")
    @JsonIgnore
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)

    private List<OrderDetail> orderDetail;


}
