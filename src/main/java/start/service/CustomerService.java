package start.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import start.entity.Customer;
import start.repository.CustomerRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer searchCustomer(long ID) {
        return customerRepository.findById(ID).orElseThrow(() ->new IllegalStateException("Customer with this" + ID + "is not found"));
    }
//    public void  updateCustomer( Long id, CustomerDTO request )  {
//        Customer cus = customerRepository.findById(id).orElseThrow(() -> new IllegalStateException("Customer with this"+id +"is not found"));
//        cus.setName(request.);
//        cus.getPhone_number(request.getPhoneNumber());
//        customerRepository.save(cus);
//
//    }

    public void deleteCustomer(long id) {
        Customer cus = customerRepository.findById(id).orElseThrow(() -> new IllegalStateException("Customer with this" + id + "is not found"));
        customerRepository.deleteById(id);
    }
}