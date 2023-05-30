package com.facuf.inventory.controller;

import com.facuf.inventory.dt.InventoryResponse;
import com.facuf.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam("sku-codes") List<String> skuCodes) {
        return ResponseEntity.ok().body(inventoryService.isInStock(skuCodes));
    }
}
