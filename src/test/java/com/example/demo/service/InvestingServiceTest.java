package com.example.demo.service;

import com.example.demo.domain.Investing;
import com.example.demo.domain.Product;
import com.example.demo.enums.StateType;
import com.example.demo.mock.InvestingMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.repository.InvestingRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.InvestProductDTO;
import com.example.demo.service.dto.InvestingProductInfo;
import com.example.demo.service.dto.InvestingProductInfoDTO;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InvestingServiceTest {

  private InvestingService investingService;

  @Mock private InvestingRepository investingRepository;

  @Mock private ProductRepository productRepository;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    investingService = new InvestingServiceImpl(investingRepository, productRepository);
  }

  @Test
  @DisplayName("투자 하기 로직 progress")
  void investProduct_progress() {

    Optional<Product> productMock = ProductMock.createdDefaultMockOptional();

    Investing investingMock = InvestingMock.createdDefaultMock(productMock.get());

    BDDMockito.given(productRepository.findById(any())).willReturn(productMock);

    BDDMockito.given(investingRepository.save(any())).willReturn(investingMock);

    InvestProductDTO dto = InvestingMock.createdInvestProductDTO(100);

    long userId = 1L;

    StateType state = investingService.investProduct(dto, userId);

    BDDMockito.then(productRepository).should().findById(any());

    BDDMockito.then(investingRepository).should().save(any());

    Assertions.assertEquals(state, StateType.PROGRESS);
  }

  @Test
  @DisplayName("투자 하기 로직 soldout")
  void investProduct_soldout() {

    Optional<Product> productMock = ProductMock.createdDefaultMockOptional();

    Investing investingMock = InvestingMock.createdDefaultMock(productMock.get());

    BDDMockito.given(productRepository.findById(any())).willReturn(productMock);

    BDDMockito.given(investingRepository.save(any())).willReturn(investingMock);

    InvestProductDTO dto = InvestingMock.createdInvestProductDTO(1000);

    long userId = 1L;

    StateType state = investingService.investProduct(dto, userId);

    BDDMockito.then(productRepository).should().findById(any());

    BDDMockito.then(investingRepository).should().save(any());

    Assertions.assertEquals(state, StateType.SOLDOUT);
  }

  @Test
  @DisplayName("내 투자자 목록 테스트 케이스")
  void getInvestingProductList() {

    Page<InvestingProductInfo> mockList = InvestingMock.createdPageMockList();

    BDDMockito.given(
            investingRepository.findById_UserId(any(), any(), eq(InvestingProductInfo.class)))
        .willReturn(mockList);

    InvestingProductInfoDTO dto = InvestingMock.createdInvestingProductInfoDTO();

    Page<InvestingProductInfo> entities = investingService.getInvestingProductList(dto, 1L);

    BDDMockito.then(investingRepository)
        .should()
        .findById_UserId(any(), any(), eq(InvestingProductInfo.class));

    org.assertj.core.api.Assertions.assertThat(entities).isEqualTo(mockList);

    mockList.forEach(
        mock -> {
          InvestingProductInfo entity =
              entities.stream()
                  .filter(productInfo -> productInfo.getProductId().equals(mock.getProductId()))
                  .findFirst()
                  .orElseThrow();

          Assertions.assertEquals(entity.getTitle(), mock.getTitle());
          Assertions.assertEquals(entity.getTotalInvestingAmount(), mock.getTotalInvestingAmount());
          Assertions.assertEquals(entity.getInvestingAmount(), mock.getInvestingAmount());
          Assertions.assertEquals(entity.getInvestingDate(), mock.getInvestingDate());
        });
  }
}
