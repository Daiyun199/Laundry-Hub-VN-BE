package start.service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.dto.request.CustomerDTO;
import start.entity.Account;
import start.entity.Customer;
import start.repository.CustomerRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class CustomerService {

//    @Autowired
//    private CustomerRepository customerRepository;
//
//    public Customer UpdateCustomer(CustomerDTO request){
//        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Customer customer = account.getCustomer();
//        customer.setAddress(request.getAddress());
//        customer.setName(request.getName());
//        customer.setPhoneNumber(request.getPhone_number());
//        customer.setAvatar(request.getAvatar());
//        return customerRepository.save(customer);
//    }
//    public Customer getCustomer(){
//        System.out.println("getCustomer run");
//        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return account.getCustomer();
//    }

}