package start.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreDTO {
    private String name;
    private String address;
    private String phoneNumber;
    private String status;
    private String coverPhoto;
}
