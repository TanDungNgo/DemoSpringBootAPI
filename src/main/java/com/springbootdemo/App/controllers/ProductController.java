package com.springbootdemo.App.controllers;

import com.springbootdemo.App.models.Product;
import com.springbootdemo.App.models.ResponseObject;

import com.springbootdemo.App.service.impl.ProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", productServiceImpl.getAll())
        );
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<ResponseObject> postBody(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert product successfully", productServiceImpl.save(product))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) {
        Optional<Product> foundProduct = productServiceImpl.getById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query product successfully", foundProduct)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find product with id = " + id, "")
                );
    }

    @PutMapping(value = "/update/{id}", consumes= "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody Product newProduct) {
        Product updatedProduct = productServiceImpl.getById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setYear(newProduct.getYear());
                    product.setPrice(newProduct.getPrice());
                    return productServiceImpl.save(product);
                }).orElseGet(()->{
                    newProduct.setId(id);
                    return productServiceImpl.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update product successfully", updatedProduct)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
        Optional<Product> foundProduct = productServiceImpl.getById(id);
        if (foundProduct.isPresent()) {
            productServiceImpl.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete product successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product with id = " + id, "")
            );
        }
    }
}
