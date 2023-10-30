package start.service;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.dto.request.OrderAdminDTO;
import start.dto.request.OrderCusDTO;
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
    private final ServiceRepository serviceRepository;
    public List<Order> getOrdersOfCustomer() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long customerId =account.getCustomer().getId();
        return orderRepository.findByCustomerId(customerId);
    }
//    public List<OrderAdminDTO> getAllOrder(){
//        return orderRepository.findAllOrderWithCustomerAndStoreInformation();
//    }
    public List<Order> getOrdersOfStore(long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BadRequest("This store doesn't exist"));
        return orderRepository.findByStoreId(storeId);
    }
    public Order addOrder(OrderCusDTO orderDTO) {
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
        order.setCustomerNumber(orderDTO.getNumberOfCustomer());
        order.setRate(0);
        order.setCustomer(customer);
        order.setNumberOfHeightCus(orderDTO.getNumberOfHeightCus());
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
            if (count == 0){
                throw new BadRequest("Please choose at least one Wash Service");
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(option.getPrice());
            orderDetail.setOption(option);
            orderDetail.setOrder(order);
            orderDetail.setService(option.getService());
            orderDetails.add(orderDetail);
            if(option.getService().getTitle() == TitleEnum.WASH){
                totalPrice += price(orderDTO.getNumberOfHeightCus(),option.getService());
            }else{
                totalPrice += option.getPrice();
            }
            store = option.getService().getStore();
        }
        order.setStore(store);
        order.setTotalPrice(totalPrice);
        order.setTotalPriceStoUp(totalPrice);
        order.setOrderDetail(orderDetails);
        return orderRepository.save(order);
    }

    public Order UpdateStatus(long orderId, OrderStatusEnum status){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("Can't not find this order"));
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
    public Order updateNumberOfHeight(long orderId, float NumberOfHeight){
        start.entity.Service service = serviceRepository.findServiceByOrderIdAndTitle(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("This order doesn't exist"));
        order.setNumberOfHeightSto(NumberOfHeight);
        order.setTotalPriceStoUp(price(NumberOfHeight,service));
        return order;
    }

    public Order RateOrder(long orderId,int rate){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("Can't not find this order"));
        if(order.getOrderStatus()!= OrderStatusEnum.DONE){
            throw new  BadRequest("Can't rate this order because it isn't finished");
        }
        return orderRepository.save(order);
    }
    public float price(float numberOfHeightCus, start.entity.Service service){
        float totalPrice =0;
        if(numberOfHeightCus <5){
            totalPrice= service.getOptions().get(0).getPrice()*numberOfHeightCus;
        }else if(numberOfHeightCus>=5 && numberOfHeightCus<7){
            totalPrice = service.getOptions().get(0).getPrice()*5 + (numberOfHeightCus-5)*service.getOptions().get(1).getPrice();
        }else if(numberOfHeightCus >=7){
            totalPrice = service.getOptions().get(0).getPrice()*5 + 2*service.getOptions().get(1).getPrice() + (numberOfHeightCus-7)*service.getOptions().get(2).getPrice();
        }
        return totalPrice;
    }
}
