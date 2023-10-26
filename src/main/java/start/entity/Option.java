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
@Table(name="Options")
public class Option {
    @Id
    @GeneratedValue
    private long id ;
    private String name;
    private float price;
//
    @ManyToOne
    @JoinColumn(name="service_id")
    private Service service;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
