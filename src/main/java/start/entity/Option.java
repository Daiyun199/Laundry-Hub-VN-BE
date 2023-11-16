package start.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import start.enums.RoleEnum;
import start.enums.StatusEnum;
import start.enums.TitleEnum;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id ;
    @Size(max = 50)
    @Column(columnDefinition = "nvarchar(max)")
    private String name;
    private float price;
    private boolean isDefaultValue;
    @OneToMany(mappedBy = "option")
    @JsonIgnore
    private List<OrderDetail> orderDetail;
    @ManyToOne
    @JoinColumn(name="service_id")
    @JsonIgnore
    private Service service;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

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
