package com.shop.betashop.controller;

import com.shop.betashop.model.Product;
import com.shop.betashop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;


@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @MockitoBean
    private ProductService productService;

    private Page<Product> productPage;

    @BeforeEach
    void setUp() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "테스트 데이터", 13000),
                new Product(1L, "테스트 데이터2", 14000)
        );

        productPage = new PageImpl<>(productList, PageRequest.of(0, 16, Sort.by("name")), productList.size());
    }

    @Test
    void shouldReturnPagedProducts() throws Exception {
        given(productService.getProductsPaged(0, 16, "name")).willReturn(productPage);
    }
}
