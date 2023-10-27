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
import java.util.ArrayList;
import java.util.Collection;
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
    private int rate;
    private OrderStatusEnum orderStatus;
    private String address;
    private float totalPrice;


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
