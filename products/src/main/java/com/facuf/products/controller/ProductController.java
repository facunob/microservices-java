package com.facuf.products.controller;

import com.facuf.products.ProductService;
import com.facuf.products.dto.ProductRequest;
import com.facuf.products.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductRequest product) {
        productService.create(product);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> list() { //This shoud return DTO instead of domain entity
        var products = productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }
}
