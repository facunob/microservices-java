package com.facuf.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="order_line_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sku_code", nullable = false)
    private String skuCode;

    @Column(name="price", nullable = false)
    private BigDecimal price;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
