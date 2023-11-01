package start.service;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import start.dto.request.OrderAdminDTO;
import start.dto.request.OrderCusDTO;
import start.entity.*;
import start.enums.OrderStatusEnum;
import start.enums.StatusEnum;
import start.enums.TitleEnum;
import start.exception.exceptions.BadRequest;
import start.repository.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public List<OrderAdminDTO> getAllOrder(){
        List<OrderAdminDTO> orderAdmin = new ArrayList<>();
        List<Order> orders = orderRepository.findAllOrderWithCustomerAndStoreInformation();
        for(Order order : orders){
            OrderAdminDTO orderDTO = new OrderAdminDTO();
            orderDTO.setId(order.getId());
            orderDTO.setAddress(order.getAddress());
            orderDTO.setNumberOfHeightSto(order.getNumberOfHeightSto());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setRate(order.getRate());
            orderDTO.setTotalPrice(order.getTotalPriceStoUp());
            orderDTO.setCustomerNumber(orderDTO.getCustomerNumber());
            orderDTO.setCustomerName(order.getCustomer().getName());
            orderDTO.setStoreName(order.getStore().getName());
            orderDTO.setOrderDetails(order.getOrderDetail());
            orderDTO.setDayCreateOrder(order.getDayCreateOrder());
            orderAdmin.add(orderDTO);
        }
       return orderAdmin;
    }
    public List<Order> getOrdersOfStore(long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BadRequest("This store doesn't exist"));
        return orderRepository.findByStoreId(storeId);
    }
    public Order addOrder(OrderCusDTO orderDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        float totalPrice = 0;
        int count = 0;
        int countOfStore =0;
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String formattedDate = dateFormat.format(today);
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
        order.setDayCreateOrder(formattedDate);
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
            if(option.getService().getStatus() == StatusEnum.DEACTIVE){
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
        if(order.getOrderStatus().equals(OrderStatusEnum.STORE_REJECT)|| order.getOrderStatus().equals(OrderStatusEnum.DONE)){
            throw new BadRequest("You can't change this order");
        }else{
            order.setOrderStatus(status);
        }
        return orderRepository.save(order);
    }
    public Order updateNumberOfHeight(long orderId, float NumberOfHeight){
        start.entity.Service service = serviceRepository.findServiceByOrderIdAndTitle(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("This order doesn't exist"));
        order.setNumberOfHeightSto(NumberOfHeight);
        order.setTotalPriceStoUp(price(NumberOfHeight,service));
        return order;
    }

    public Order RateOrder(long orderId,float rate){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer cus = account.getCustomer();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("Can't not find this order"));
        int count =0;
        for(Order order1 : account.getCustomer().getOrders()){
            if(order1.getId() == order.getId()){
                count +=1;
            }
        }
        if(order.getOrderStatus()!= OrderStatusEnum.DONE || count == 1){
            throw new  BadRequest("Can't rate this order because it isn't finished or it isn't yours");
        } else{
            order.setRate(rate);
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
    public int countOrderOnProcess(){
        int count =0;
        List<Order> orders = orderRepository.findAll();
        for(Order order : orders){
            if(!(order.getOrderStatus().equals(OrderStatusEnum.DONE) || order.getOrderStatus().equals(OrderStatusEnum.STORE_REJECT))){
                count++;
            }
        }
        return count ;
    }
    public Order getOrderbyId(long orderId) {
//        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("This order doesn't exist"));
        return order;

    }
}
