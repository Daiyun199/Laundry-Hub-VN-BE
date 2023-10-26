package start.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private String name;
    private String phone_number;
    private String avatar;
    private String address;
}
