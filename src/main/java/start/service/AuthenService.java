package start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import start.dto.request.LoginRequestDTO;
import start.dto.request.SignUpRequestDTO;
import start.dto.response.LoginResponse;
import start.entity.Account;
import start.repository.UserRepository;
import start.utils.TokenHandler;

@Service
public class AuthenService implements UserDetailsService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenHandler tokenHandler;

    public LoginResponse login(LoginRequestDTO loginRequestDTO){
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );

        } catch (Exception e) {
//            throw new EntityNotFound("Username or password invalid");
            e.printStackTrace();
        }
        Account account = (Account) authentication.getPrincipal();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(account.getUsername());
        loginResponse.setRole(account.getRole());
        loginResponse.setToken(tokenHandler.generateToken(account));
        return loginResponse;
    }

    public Account signUp(SignUpRequestDTO signUpRequestDTO){
        Account account = new Account();
        account.setUsername(signUpRequestDTO.getUsername());
        account.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        return userRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userRepository.findUserByUsername(username);
        return account;
    }
}
