package start.service;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.dto.request.OrderDTO;
import start.entity.*;
import start.enums.OrderStatusEnum;
import start.enums.ServiceStatusEnum;
import start.enums.TitleEnum;
import start.exception.exceptions.BadRequest;
import start.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final OptionRepository optionRepository;

    public List<Order> getOrdersOfCustomer() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long customerId =account.getCustomer().getId();
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersOfStore() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long storeId = account.getStore().getId();
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BadRequest("This store doesn't exist"));
        return orderRepository.findByStoreId(storeId);
    }

    public Order addOrder(OrderDTO orderDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        float totalPrice = 0;
        int count = 0;
        int countOfStore =0;
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        Store store = null;
        Customer customer = customerRepository.findById(account.getCustomer().getId()).orElseThrow(() -> new BadRequest("This customer doesn't exist"));
        order.setAddress(orderDTO.getAddress());
        order.setOrderStatus(OrderStatusEnum.CREATE_ORDER);
        order.setRate(0);
        order.setCustomer(customer);
        boolean hasDuplicates = orderDTO.getOptionIds().stream().distinct().count() < orderDTO.getOptionIds().size();
        if (hasDuplicates) throw new BadRequest("Only choose one option one time!");
        for (Long optionId : orderDTO.getOptionIds()) {
            Option option = optionRepository.findById(optionId).orElseThrow(() -> new BadRequest("Cant find this option"));
            if (option.getService().getTitle() == TitleEnum.WASH) {
                if (count == 0) {
                    count++;
                } else {
                    throw new BadRequest("Only Choose one WASH!");
                }
            }
            if(store!=null && option.getService().getStore()!=store){
                throw new BadRequest("Only choose options in the same store");
            }
            if(option.getService().getStatus() == ServiceStatusEnum.DEACTIVE){
                throw new BadRequest("This option doesn't active");
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(option.getPrice());
            orderDetail.setOption(option);
            orderDetail.setOrder(order);
            orderDetail.setService(option.getService());
            orderDetails.add(orderDetail);
            totalPrice += option.getPrice();
            store = option.getService().getStore();
        }
        order.setStore(store);
        order.setTotalPrice(totalPrice);
        order.setOrderDetail(orderDetails);
        return orderRepository.save(order);
    }

    public Order UpdateStatus(long orderId, OrderStatusEnum status){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("Can't not find this order"));
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }


}
