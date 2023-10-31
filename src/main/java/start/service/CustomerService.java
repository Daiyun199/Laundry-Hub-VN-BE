package start.service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.dto.request.CustomerDTO;
import start.entity.Account;
import start.entity.Customer;
import start.enums.RoleEnum;
import start.enums.StatusEnum;
import start.exception.exceptions.BadRequest;
import start.repository.CustomerRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer UpdateCustomer(CustomerDTO request){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = account.getCustomer();
        customer.setAddress(request.getAddress());
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhone_number());
        customer.setAvatar(request.getAvatar());
        return customerRepository.save(customer);
    }
    public Customer getCustomer(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return account.getCustomer();
    }

    public List<Customer> getAllCustomer(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account.getRole().equals(RoleEnum.ADMIN)){
           return customerRepository.findAll();
        }else{
            throw new BadRequest("You don't have permission to use this function");
        }
    }


}