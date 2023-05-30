package com.facuf.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//        return args -> {
//            Inventory inventory = new Inventory();
//            inventory.setSkuCode("iphone_13");
//            inventory.setAmount(50);
//
//            Inventory inventory2 = new Inventory();
//            inventory2.setSkuCode("galaxy_s22");
//            inventory2.setAmount(60);
//
//            inventoryRepository.saveAll(Arrays.asList(inventory2, inventory));
//        };
//    }
}
