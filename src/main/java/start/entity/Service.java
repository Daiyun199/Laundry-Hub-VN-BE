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
@Table(name="Services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private float price;
    private String description;
    private String figure;


    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

    @OneToMany(mappedBy = "service")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "service")
    private List<Option> options;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
