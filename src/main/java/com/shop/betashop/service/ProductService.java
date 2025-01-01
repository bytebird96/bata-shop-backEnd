package com.shop.betashop.service;

import com.shop.betashop.controller.ProductController;
import com.shop.betashop.model.Product;
import com.shop.betashop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProductsPaged(int page, int size, String sort){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,sort));
        return productRepository.findAll(pageRequest);
    }

}
