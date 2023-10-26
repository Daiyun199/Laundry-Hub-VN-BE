package start.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    private String status;
    private String coverPhoto;

    @OneToOne
    @JoinColumn(name="account_id")
    @JsonIgnore
    private Account account;

    @OneToMany(mappedBy = "store")
    private List<Order> orders;

    @OneToMany(mappedBy = "store")
    private List<Service> services;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
