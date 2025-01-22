package com.shop.betashop.controller;

import com.shop.betashop.model.Product;
import com.shop.betashop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void testGetProducts() throws Exception {
        Page<Product> mockPage = new PageImpl<>(Collections.emptyList());
        when(productService.getProductsPaged(0, 16, "name")).thenReturn(mockPage);

        mockMvc.perform(get("/api/products/getProducts?page=0&size=16&sort=name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());

        verify(productService, times(1)).getProductsPaged(0, 16, "name");
    }

}
