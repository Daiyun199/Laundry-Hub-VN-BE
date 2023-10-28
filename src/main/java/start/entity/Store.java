package start.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import start.enums.ServiceStatusEnum;

import javax.persistence.*;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private ServiceStatusEnum status;
    private String coverPhoto;

    @OneToOne
    @JoinColumn(name="account_id")
    @JsonIgnore
    private Account account;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<Service> services;

    public void setId(long id) {
        this.id = id;
    }


    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
