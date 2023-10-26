package start.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String name;
    private String phoneNumber;
    private String avatar;
    private String address;

    @OneToOne
    @JoinColumn(name="account_id")
    @JsonIgnore
    private Account account;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
       this.phoneNumber = phoneNumber;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
