package start.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import start.dto.request.LoginRequestDTO;
import start.dto.request.SignUpRequestDTO;
import start.dto.response.LoginResponse;
import start.entity.Account;
import start.entity.Customer;
import start.entity.Store;
import start.enums.RoleEnum;
import start.enums.ServiceStatusEnum;
import start.repository.CustomerRepository;
import start.repository.StoreRepository;
import start.repository.UserRepository;
import start.utils.TokenHandler;

@Service
@AllArgsConstructor
public class AccountService {
    private final UserRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenHandler tokenHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void addAccount(SignUpRequestDTO signUpData) {
        Account account = new Account();

        account.setUsername(signUpData.getUsername());
        account.setPassword(passwordEncoder.encode(signUpData.getPassword()));
        if (signUpData.getRole() == RoleEnum.CUSTOMER){
            account.setRole(RoleEnum.CUSTOMER);
            Customer cus = new Customer();
            cus.setName(signUpData.getCustomer().getName());
            cus.setPhoneNumber(signUpData.getCustomer().getPhone_number());
            cus.setAddress(signUpData.getCustomer().getAddress());
            cus.setAvatar(signUpData.getCustomer().getAvatar());
            cus.setAccount(account);
            account.setCustomer(cus);
            accountRepository.save(account);
        } else if (signUpData.getRole() == RoleEnum.STORE){
            account.setRole(RoleEnum.STORE);
            Store store = new Store();
            store.setName(signUpData.getStore().getName());
            store.setAddress(signUpData.getStore().getAddress());
            store.setStatus(ServiceStatusEnum.ACTIVE);
            store.setCoverPhoto(signUpData.getStore().getCoverPhoto());
            store.setPhoneNumber(signUpData.getStore().getPhoneNumber());
            store.setDescription(signUpData.getStore().getDescription());
            store.setAccount(account);
            account.setStore(store);
            accountRepository.save(account);
        } else if (signUpData.getRole() == RoleEnum.ADMIN){
            account.setRole(RoleEnum.ADMIN);
            accountRepository.save(account);
        }
    }
    public LoginResponse login(LoginRequestDTO loginRequestDTO){
        Authentication authentication = null;

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );


        Account account = accountRepository.findUserByUsername(((Account) authentication.getPrincipal()).getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(account.getUsername());
        loginResponse.setRole(account.getRole());
        loginResponse.setToken(tokenHandler.generateToken(account));
        if(account.getRole() == RoleEnum.CUSTOMER){
           loginResponse.setCustomer(account.getCustomer());
        }else if(account.getRole() == RoleEnum.STORE){
            loginResponse.setStore(account.getStore());
        }
        return loginResponse;
    }
}
