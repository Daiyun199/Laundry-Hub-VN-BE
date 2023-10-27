package start.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import start.enums.ServiceStatusEnum;
import start.enums.TitleEnum;

import javax.persistence.*;
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
    private ServiceStatusEnum status;
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
