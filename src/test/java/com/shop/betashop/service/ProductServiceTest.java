package com.shop.betashop.service;

import com.shop.betashop.model.Product;
import com.shop.betashop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetProductsPaged() {
        PageRequest pageRequest = PageRequest.of(0, 16, Sort.by(Sort.Direction.DESC, "name"));
        Page<Product> mockPage = new PageImpl<>(Collections.emptyList());

        when(productRepository.findAll(pageRequest)).thenReturn(mockPage);

        Page<Product> result = productService.getProductsPaged(0, 16, "name");

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(productRepository, times(1)).findAll(pageRequest);
    }
}
