package start.entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import start.enums.RoleEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float price;

    @ManyToOne
    @JoinColumn(name="service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    public void setId(long id) {
        this.id = id;
    }
   @ManyToOne
   @JoinColumn(name="option_id")
    private Option option;

    public void setPrice(float price) {
        this.price = price;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
