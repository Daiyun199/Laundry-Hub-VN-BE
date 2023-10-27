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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String name;
    private String description;
    private String figure;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Enumerated(EnumType.STRING)
    private TitleEnum title;



    @ManyToOne
    @JoinColumn(name="store_id")
    @JsonIgnore
    private Store store;



    @OneToMany(mappedBy = "service")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "service")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Option> options;
}
