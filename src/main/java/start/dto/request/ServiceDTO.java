package start.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceDTO {
    private String description;
    private String figure;
    private String name;
    private float price;

}
