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
            orderDTO.setTotalPriceOfCus(order.getTotalPrice());
            orderDTO.setNumberOfHeightCus(order.getNumberOfHeightCus());
            orderDTO.setCustomerNumber(order.getCustomerNumber());
            orderDTO.setCustomerName(order.getCustomer().getName());
            orderDTO.setStoreName(order.getStore().getName());
            orderDTO.setOrderDetails(order.getOrderDetail());
            orderDTO.setDayCreateOrder(order.getDayCreateOrder());
            orderAdmin.add(orderDTO);
        }
       return orderAdmin;
    }
    public List<OrderAdminDTO> getOrdersOfStore() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = account.getStore();
        List<OrderAdminDTO> orderAdmin = new ArrayList<>();
        List<Order> orders = orderRepository.findByStoreId(store.getId());
        for(Order order : orders){
            OrderAdminDTO orderDTO = new OrderAdminDTO();
            orderDTO.setId(order.getId());
            orderDTO.setAddress(order.getAddress());
            orderDTO.setNumberOfHeightSto(order.getNumberOfHeightSto());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setRate(order.getRate());
            orderDTO.setTotalPriceOfCus(order.getTotalPrice());
            orderDTO.setNumberOfHeightCus(order.getNumberOfHeightCus());
            orderDTO.setTotalPrice(order.getTotalPriceStoUp());
            orderDTO.setCustomerNumber(order.getCustomerNumber());
            orderDTO.setCustomerName(order.getCustomer().getName());
            orderDTO.setOrderDetails(order.getOrderDetail());
            orderDTO.setDayCreateOrder(order.getDayCreateOrder());
            orderAdmin.add(orderDTO);
        }
        return orderAdmin;
    }
    public Order addOrder(OrderCusDTO orderDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerRepository.findById(account.getCustomer().getId()).orElseThrow(() -> new BadRequest("This customer doesn't exist"));
        if(customer.getStatus() == StatusEnum.ACTIVE){
            float totalPrice = 0;
            int count = 0;
            int countOfStore =0;
            Date today = new Date();
            Order order = new Order();
            List<OrderDetail> orderDetails = new ArrayList<>();
            Store store = null;
            order.setAddress(orderDTO.getAddress());
            order.setOrderStatus(OrderStatusEnum.CREATE_ORDER);
            order.setCustomerNumber(orderDTO.getNumberOfCustomer());
            order.setRate(0);
            order.setCustomer(customer);
            order.setNumberOfHeightCus(orderDTO.getNumberOfHeightCus());
            order.setDayCreateOrder(today);
            order.setNumberOfHeightSto(orderDTO.getNumberOfHeightCus());
            boolean hasDuplicates = orderDTO.getOptionIds().stream().distinct().count() < orderDTO.getOptionIds().size();
            if (hasDuplicates) throw new BadRequest("Only choose one option one time!");
            for (Long optionId : orderDTO.getOptionIds()) {
                Option option = optionRepository.findById(optionId).orElseThrow(() -> new BadRequest("Cant find this option"));
                if(option.getStatus() == StatusEnum.ACTIVE){
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
                }else{

                    throw new BadRequest("This options '"+option.getName()+"' is deactive now," +
                            " please choose other option");
                }

            }
            order.setStore(store);
            order.setTotalPrice(totalPrice);
            order.setTotalPriceStoUp(totalPrice);
            order.setOrderDetail(orderDetails);
            return orderRepository.save(order);
        }else{
            throw new BadRequest("Your account is block and can't order service now, please contact to admin to unblock your account");
        }
    }

    public Order UpdateStatus(long orderId, OrderStatusEnum status,String... feedback){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("Can't not find this order"));
        if(order.getOrderStatus().equals(OrderStatusEnum.STORE_REJECT)|| order.getOrderStatus().equals(OrderStatusEnum.DONE)){
            throw new BadRequest("You can't change this order");
        }else{
            if(status == OrderStatusEnum.DONE){
                order.setDateOrderDone(new Date());
            }
            order.setOrderStatus(status);
            if(status == OrderStatusEnum.STORE_REJECT || feedback.length>0){
                order.setFeedbackFromStore(feedback[0]);
            }
        }
        return orderRepository.save(order);
    }
    public Order updateNumberOfHeight(long orderId, float NumberOfHeight){
        start.entity.Service service = serviceRepository.findServiceByOrderIdAndTitle(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("This order doesn't exist"));
        order.setNumberOfHeightSto(NumberOfHeight);
        order.setTotalPriceStoUp(price(NumberOfHeight,service));
        return orderRepository.save(order);
    }

    public Order RateOrder(long orderId,float rate,String feedback){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer cus = account.getCustomer();

            Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("Can't not find this order"));
            Customer cus2 = customerRepository.findCustomerByOrdersId(orderId);
//        int count =0;
//        for(Order order1 : orders){
//            if(order1.getId() == order.getId()){
//                count +=1;
//            }
//        }

            // đây là check xem order được nhập vào có phải của người đang đăng nhập hay không

            if(order.getOrderStatus()!= OrderStatusEnum.DONE || cus.getId() != cus2.getId()){
                throw new  BadRequest("Can't rate this order because it isn't finished or it isn't yours");
            } else{
                order.setRate(rate);
                order.setFeedback(feedback);
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
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = account.getStore();
        Store store2 = storeRepository.findStoreByOrdersId(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("This Order doesn't exist"));
        if (store.getId() == store2.getId()) {
            return order;
        } else {
            throw new BadRequest("You don't have permission to view order");
        }
    }
    //Customer xem lại order của mình
    public Order viewOrderByCustomer(long orderId){
        Account account= (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer cus = account.getCustomer();
        Customer cus2 = customerRepository.findCustomerByOrdersId(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BadRequest("This order doesn't exist"));
        if(cus.getId() == cus2.getId()){
            return order;
        }else {
            throw new BadRequest("This order isn't your");
        }
    }
    public double TotalPrice(){
        double totalPrice =0;
        List<Order> orders = orderRepository.findAll();
        for(Order order : orders){
            if(order.getOrderStatus().equals(OrderStatusEnum.DONE)){
                totalPrice = order.getTotalPrice();
            }
        }
        return totalPrice;
    }
}
