package com.evolocity.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> listAll() {
        log.info("Return all products");
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product get(@PathVariable Long id) {
        log.info("Return product with id {}", id);
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException());
    }

    @PostMapping("/products")
    public Product save(@RequestBody Product product) {
        log.info("Save product");
        return productRepository.save(product);
    }

    @DeleteMapping("/products/{id}")
    void delete(@PathVariable Long id) {
        log.info("Delete product with id {}", id);
        productRepository.deleteById(id);
    }
}




