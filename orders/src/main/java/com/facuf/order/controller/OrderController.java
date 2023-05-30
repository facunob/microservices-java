package com.facuf.order.controller;

import com.facuf.order.dto.OrderRequest;
import com.facuf.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public ResponseEntity<Void> placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return ResponseEntity.noContent().build();
    }

    public String fallbackMethod(OrderRequest orderRequest, RuntimeException e) {
        return "Oops! something went wrong.";
    }

    @PostMapping("/{orderNumber}/notify")
    public ResponseEntity<Void> sendNotificationOrder(@PathVariable("orderNumber") String orderNumber) {
        orderService.sendOrderNotification(orderNumber);
        return ResponseEntity.noContent().build();
    }
}
