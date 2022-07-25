package com.food.ordering.system.order.service.domain.ports;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.ordering.food.system.order.service.domain.OrderDomainService;
import com.ordering.food.system.order.service.domain.entity.Customer;
import com.ordering.food.system.order.service.domain.entity.Order;
import com.ordering.food.system.order.service.domain.entity.Restaurant;
import com.ordering.food.system.order.service.domain.events.OrderCreatedEvent;
import com.ordering.food.system.order.service.domain.exceptions.OrderDomainException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class CreateOrderCommandHelper {

    private final OrderDataMapper orderDataMapper;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;

    public CreateOrderCommandHelper(OrderDataMapper orderDataMapper,
                                    CustomerRepository customerRepository,
                                    RestaurantRepository restaurantRepository,
                                    OrderRepository orderRepository,
                                    OrderDomainService orderDomainService) {
        this.orderDataMapper = orderDataMapper;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
        this.orderDomainService = orderDomainService;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand){
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.orderCreateCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order,restaurant);
        saveOrder(order);
        return orderCreatedEvent;
    }

    private Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        if(savedOrder == null){
            throw new OrderDomainException("could not save order");
        }
        return order;
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.orderCreateCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> restaurantInformation = restaurantRepository.findRestaurantInformation(restaurant);
        if(restaurantInformation.isEmpty()){
            throw new OrderDomainException("could not find restaurant with restaurant information "
                    + restaurant.getId() );
        }
        return restaurantInformation.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if(customer.isEmpty()){
            throw new OrderDomainException("could not find customer with customer id " + customerId);
        }
    }

}
