package com.food.ordering.system.order.service.domain.ports;

import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.ordering.food.system.order.service.domain.entity.Order;
import com.ordering.food.system.order.service.domain.exceptions.OrderNotFoundException;
import com.ordering.food.system.order.service.domain.valueobjects.TrackingId;

import java.util.Optional;

public class TrackOrderQueryHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public TrackOrderQueryHandler(OrderDataMapper orderDataMapper,
                                  OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    public TrackOrderResponse handleTrackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> order = orderRepository.findOrderByTrackingID
                                                (new TrackingId(trackOrderQuery.getTrackingId()));
        if(order.isEmpty()){
            throw new OrderNotFoundException("order not found with tracking id "
                                                + trackOrderQuery.getTrackingId());
        }
        return orderDataMapper.orderToTrackingResponse(order.get());
    }
}
