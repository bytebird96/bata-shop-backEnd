package com.shop.betashop.service;

import com.shop.betashop.model.Product;
import com.shop.betashop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private List<Product> productList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productList = Arrays.asList(
                new Product(1L, "Product A", 1000),
                new Product(2L, "Product B", 2000)
        );
    }

    @Test
    @DisplayName("메인화면 목록 조회 서버스 단")
    void shouldReturnPagedProducts() {
        //Given
        int page = 0;
        int size = 2;
        String sort = "name";

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
        Page<Product> productPage = new PageImpl<>(productList, pageRequest, productList.size());

        given(productRepository.findAll(pageRequest)).willReturn(productPage);

        // When
        Page<Product> result = productService.getProductsPaged(page, size, sort);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Product A");
        verify(productRepository, times(1)).findAll(pageRequest);
    }
}
