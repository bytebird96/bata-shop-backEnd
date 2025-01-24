package com.shop.betashop.controller;

import com.shop.betashop.model.Product;
import com.shop.betashop.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        }
)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    private Page<Product> productPage;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        List<Product> productList = Arrays.asList(
                new Product(1L, "테스트 데이터", 13000),
                new Product(2L, "테스트 데이터2", 14000)
        );

        productPage = new PageImpl<>(productList, PageRequest.of(0, 16, Sort.by("name")), productList.size());
    }

    @Test
    @DisplayName("메인화면 목록 조회")
    void shouldReturnPagedProducts() throws Exception {
        // Given
        given(productService.getProductsPaged(0, 16, "name")).willReturn(productPage);

        // When & Then
        mockMvc.perform(get("/api/products/getProducts")
                        .param("page", "0")    // 페이지 번호 0
                        .param("size", "16")   // 페이지 크기 16
                        .param("sort", "name") // 정렬 기준 name
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 응답 확인
                .andExpect(jsonPath("$.content.length()").value(2)) // content 배열 크기 확인
                .andExpect(jsonPath("$.content[0].name").value("테스트 데이터")) // 첫 번째 항목 이름 확인
                .andExpect(jsonPath("$.content[1].name").value("테스트 데이터2"));

        // Verify
        verify(productService, times(1)).getProductsPaged(0, 16, "name");
    }
}
