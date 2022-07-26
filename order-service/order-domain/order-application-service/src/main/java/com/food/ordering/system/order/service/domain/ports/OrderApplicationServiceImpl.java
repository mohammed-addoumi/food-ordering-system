package com.food.ordering.system.order.service.domain.ports;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import org.springframework.stereotype.Service;

@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final CreateOrderCommandHandler createOrderCommandHandler;
    private final TrackOrderQueryHandler trackOrderQueryHandler;

    public OrderApplicationServiceImpl(CreateOrderCommandHandler createOrderCommandHandler,
                                       TrackOrderQueryHandler trackOrderQueryHandler) {
        this.createOrderCommandHandler = createOrderCommandHandler;
        this.trackOrderQueryHandler = trackOrderQueryHandler;
    }


    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return createOrderCommandHandler.handleCreateOrderCommand(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {

        return trackOrderQueryHandler.handleTrackOrder(trackOrderQuery);
    }
}
