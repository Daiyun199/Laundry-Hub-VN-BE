package start.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import start.enums.RoleEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {
    private String username;
    private String password;

    private RoleEnum role;
    CustomerDTO customer;
    StoreDTO store;
}
