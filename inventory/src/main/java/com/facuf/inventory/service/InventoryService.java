package com.facuf.inventory.service;

import com.facuf.inventory.dt.InventoryResponse;
import com.facuf.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        return inventoryRepository
                .findBySkuCodeIn(skuCodes)
                .stream()
                .map(i ->
                        InventoryResponse
                                .builder()
                                .skuCode(i.getSkuCode())
                                .isInStock(i.getAmount() > 0)
                                .build()
                ).toList();
    }

}
