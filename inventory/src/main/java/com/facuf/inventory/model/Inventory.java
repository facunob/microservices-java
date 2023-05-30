package com.facuf.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}