package com.springbootdemo.App.service.impl;

import com.springbootdemo.App.models.Product;
import com.springbootdemo.App.repositories.ProductRepository;
import com.springbootdemo.App.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getById(Long id) {
        return Optional.ofNullable(productRepository.findById(id))
                .orElseThrow(() -> new UsernameNotFoundException("Product by " + id + " can not found"));
    }

    @Override
    public Product save(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public Product update(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
