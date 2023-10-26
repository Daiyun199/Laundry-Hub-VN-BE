package start.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import start.dto.request.LoginRequestDTO;
import start.dto.request.SignUpRequestDTO;
import start.dto.response.LoginResponse;
import start.entity.Account;
import start.service.AccountService;
import start.service.AuthenService;
import start.utils.ResponseHandler;

@RestController
public class Authentication {

    @Autowired
    AuthenService authenService;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO){
        System.out.println(loginRequestDTO.getUsername());
        LoginResponse user = accountService.login(loginRequestDTO);
        return responseHandler.response(200, "Login success!", user);
    }


    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignUpRequestDTO signUpRequestDTO){

        accountService.addAccount(signUpRequestDTO);
        return responseHandler.response(200, "Sign Up success!", null);
    }
}
