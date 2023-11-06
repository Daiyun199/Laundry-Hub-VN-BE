package start.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import start.dto.request.LoginRequestDTO;
import start.dto.request.SignUpRequestDTO;
import start.dto.response.LoginResponse;
import start.entity.Account;
import start.entity.Customer;
import start.entity.Store;
import start.enums.RoleEnum;
import start.enums.StatusEnum;
import start.exception.exceptions.BadRequest;
import start.repository.CustomerRepository;
import start.repository.StoreRepository;
import start.repository.UserRepository;
import start.utils.TokenHandler;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        account.setDateCreate(new Date());
        if (signUpData.getRole() == RoleEnum.CUSTOMER){
            account.setRole(RoleEnum.CUSTOMER);
            Customer cus = new Customer();
            cus.setName(signUpData.getCustomer().getName());
            cus.setPhoneNumber(signUpData.getCustomer().getPhone_number());
            cus.setAddress(signUpData.getCustomer().getAddress());
            cus.setAvatar(signUpData.getCustomer().getAvatar());
            cus.setAccount(account);
            cus.setStatus(StatusEnum.ACTIVE);
            account.setCustomer(cus);
            accountRepository.save(account);
        } else if (signUpData.getRole() == RoleEnum.STORE){
            account.setRole(RoleEnum.STORE);
            Store store = new Store();
            store.setName(signUpData.getStore().getName());
            store.setAddress(signUpData.getStore().getAddress());
            store.setStatus(StatusEnum.DEACTIVE);
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
    public void deactiveCustomer(long customerId){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Account acc = accountRepository.findById(accountId).orElseThrow(() -> new BadRequest("This account doesn't exist"));
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new BadRequest("This customer doesn't exist"));
        if(account.getRole().equals(RoleEnum.ADMIN)){
                cus.setStatus(StatusEnum.DEACTIVE);
        }else{
            throw new BadRequest("You don't have permission to delete this account");
        }
        customerRepository.save(cus);
    }
    public void activeCustomer(long customerId){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer cus = customerRepository.findById(customerId).orElseThrow(() -> new BadRequest("This customer doesn't exist"));
        if(account.getRole().equals(RoleEnum.ADMIN)){
            cus.setStatus(StatusEnum.ACTIVE);
        }else{
            throw new BadRequest("You don't have permission to delete this account");
        }
        customerRepository.save(cus);
    }
    public void deactiveStore(long StoreId){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = storeRepository.findById(StoreId).orElseThrow(() -> new BadRequest("This store doesn't exist"));
        if(account.getRole().equals(RoleEnum.ADMIN)){
            store.setStatus(StatusEnum.DEACTIVE);
        }else{
            throw new BadRequest("You don't have permission to delete this account");
        }
       storeRepository.save(store);
    }
    public void activeStore(long StoreId){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = storeRepository.findById(StoreId).orElseThrow(() -> new BadRequest("This store doesn't exist"));
        if(account.getRole().equals(RoleEnum.ADMIN)){
            store.setStatus(StatusEnum.ACTIVE);
        }else{
            throw new BadRequest("You don't have permission to delete this account");
        }
        storeRepository.save(store);
    }
//    public int getAccountList(){
//
//        Date today = new Date();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(today);
//        calendar.set(,Calendar.DAY_OF_MONTH, 1); // Thiết lập ngày là 1 (ngày đầu tháng)
//        Date startDate = calendar.getTime();
//
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Thiết lập ngày là cuối tháng
//        Date endDate = calendar.getTime();
//
//        return accounts.size();
//    }

    public List<Integer> getRegisteredAccountsCountForMonthAndDay() {

        List<Integer> countAccount = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(currentDate);
        int year =currentYearMonth.getYear();
        int month = currentYearMonth.getMonthValue();

        if(month == 2){
            List<Account> account2MonthAgo = accountRepository.findByCreatedAtMonthYear(year-1, 12);
            countAccount.add(account2MonthAgo.size());
            List<Account> accountLastMonth = accountRepository.findByCreatedAtMonthYear(year, month-1);
            countAccount.add(accountLastMonth.size());
            List<Account> accountNow = accountRepository.findByCreatedAtMonthYear(year, month);
            countAccount.add(accountNow.size());
        }else if(month == 1){
            List<Account> account2MonthAgo = accountRepository.findByCreatedAtMonthYear(year-1, 11);
            countAccount.add(account2MonthAgo.size());
            List<Account> accountLastMonth = accountRepository.findByCreatedAtMonthYear(year-1, 12);
            countAccount.add(accountLastMonth.size());
            List<Account> accountNow = accountRepository.findByCreatedAtMonthYear(year, month);
            countAccount.add(accountNow.size());
        }else{
            List<Account> account2MonthAgo = accountRepository.findByCreatedAtMonthYear(year, month-2);
            countAccount.add(account2MonthAgo.size());
            List<Account> accountLastMonth = accountRepository.findByCreatedAtMonthYear(year, month-1);
            countAccount.add(accountLastMonth.size());
            List<Account> accountNow = accountRepository.findByCreatedAtMonthYear(year, month);
            countAccount.add(accountNow.size());
        }

        return countAccount;
    }
}
