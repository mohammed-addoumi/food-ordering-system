package com.food.ordering.system.order.service.domain.ports;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.ordering.food.system.order.service.domain.events.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final CreateOrderCommandHelper createOrderCommandHelper;
    private final OrderCreatedPaymentRequestMessagePublisher publisher;

    public CreateOrderCommandHandler(OrderDataMapper orderDataMapper,
                                     CreateOrderCommandHelper customerRepository,
                                     OrderCreatedPaymentRequestMessagePublisher publisher) {
        this.orderDataMapper = orderDataMapper;
        this.createOrderCommandHelper = customerRepository;
        this.publisher = publisher;
    }


    public CreateOrderResponse handleCreateOrderCommand(CreateOrderCommand createOrderCommand){

        OrderCreatedEvent orderCreatedEvent = createOrderCommandHelper.persistOrder(createOrderCommand);
        publisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),
                                                    "");
    }


}
