package start.dto.response;

import lombok.Data;
import start.entity.Account;

@Data
public class LoginResponse extends Account {
    String token;

}
