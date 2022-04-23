package com.example.demo.service;

import com.example.demo.mock.ProductMock;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductInfo;
import com.example.demo.service.dto.ProductInfoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {

  private ProductService productService;

  @Mock private ProductRepository productRepository;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    productService = new ProductServiceImpl(productRepository);
  }

  @Test
  @DisplayName("투자자 리스트 업")
  void getProductList() {

    Page<ProductInfo> mockList = ProductMock.cratedProductInfoToPage();

    BDDMockito.given(
            productRepository.findByStartedAtGreaterThanEqualAndFinishedAtLessThan(
                any(), any(), any()))
        .willReturn(mockList);

    ProductInfoDTO dto = ProductMock.createdProductInfoDTO();

    Page<ProductInfo> entities = productService.getProductList(dto);

    BDDMockito.then(productRepository)
        .should()
        .findByStartedAtGreaterThanEqualAndFinishedAtLessThan(any(), any(), any());

    List<ProductInfo> content = entities.getContent();

    content.forEach(
        entity -> {
          ProductInfo mock =
              mockList.stream()
                  .filter(value -> value.getProductId().equals(entity.getProductId()))
                  .findFirst()
                  .orElseThrow();

          Assertions.assertEquals(entity.getTitle(), mock.getTitle());
          Assertions.assertEquals(entity.getTotalInvestingAmount(), mock.getTotalInvestingAmount());
          Assertions.assertEquals(
              entity.getCurrentInvestingAmount(), mock.getCurrentInvestingAmount());
          Assertions.assertEquals(entity.getStartedAt(), mock.getStartedAt());
          Assertions.assertEquals(entity.getFinishedAt(), mock.getFinishedAt());
          Assertions.assertEquals(entity.getState(), mock.getState());
          Assertions.assertEquals(entity.getMemberCount(), mock.getMemberCount());
        });
  }
}
