package start.entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
    private long id;
    private String status;
    private int rate;
    private String orderStatus;
    private String address;
    private float totalPrice;


    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetail;

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
