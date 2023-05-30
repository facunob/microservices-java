package com.facuf.order.service;

import com.facuf.order.dto.InventoryResponse;
import com.facuf.order.dto.OrderLineItemsDTO;
import com.facuf.order.dto.OrderRequest;
import com.facuf.order.event.OrderPlacedEvent;
import com.facuf.order.model.Order;
import com.facuf.order.model.OrderLineItems;
import com.facuf.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderItemsList(orderRequest.getOrderLineItems().stream().map(this::mapToOrderLineItems).toList());

        var skuCodes = order.getOrderItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        var inventories = webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder.path("http://inventory-service/api/inventory").queryParam("sku-codes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if(inventories == null || Arrays.stream(inventories).allMatch(InventoryResponse::isInStock)) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
        }

        // throw not stock exception
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDTO dto) {
        var orderLineItem = new OrderLineItems();
        orderLineItem.setAmount(dto.getAmount());
        orderLineItem.setPrice(dto.getPrice());
        orderLineItem.setSkuCode(dto.getSkuCode());
        orderLineItem.setId(dto.getId());
        return orderLineItem;
    }

    public void sendOrderNotification(String orderNumber) {
        kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(orderNumber));
    }
}
