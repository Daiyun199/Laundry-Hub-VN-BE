package start.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import start.enums.ServiceStatusEnum;
import start.enums.TitleEnum;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceDTO {
    private String description;
    private String figure;
    private String name;
    private float price;
    private ServiceStatusEnum status;
    private TitleEnum title;

}
