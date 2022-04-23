package com.example.demo.repository;

import com.example.demo.config.JpaConfiguration;
import com.example.demo.domain.Investing;
import com.example.demo.domain.InvestingKey;
import com.example.demo.domain.Product;
import com.example.demo.mock.InvestingMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.service.dto.InvestingProductInfo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(JpaConfiguration.class)
class InvestingRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @Autowired private InvestingRepository investingRepository;

  private Product product;

  @BeforeEach
  void init() {

    product = productRepository.save(ProductMock.createdDefaultMock());

    productRepository.flush();
  }

  @Test
  @DisplayName("저장로직")
  void save() {

    Investing mock = InvestingMock.createdDefaultMock(product);

    Investing entity = investingRepository.save(mock);

    investingRepository.flush();

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);

    Assertions.assertEquals(entity.getId().getProductId(), mock.getId().getProductId());
    Assertions.assertEquals(entity.getId().getUserId(), mock.getId().getUserId());
    Assertions.assertEquals(entity.getInvestingAmount(), mock.getInvestingAmount());
  }

  @Nested
  @DisplayName("조회")
  class Select {

    Investing mock;

    @BeforeEach
    void init() {

      mock = investingRepository.save(InvestingMock.createdDefaultMock(product));

      investingRepository.flush();
    }

    @Test
    @DisplayName("아이디 별로 조회")
    void findById() {

      InvestingKey id = InvestingKey.builder().productId(1L).userId(1L).build();

      Optional<Investing> entityOptional = investingRepository.findById(id);

      Assertions.assertTrue(entityOptional.isPresent());

      Investing entity = entityOptional.get();

      Assertions.assertEquals(entity.getId().getProductId(), mock.getId().getProductId());
      Assertions.assertEquals(entity.getId().getUserId(), mock.getId().getUserId());
      Assertions.assertEquals(entity.getInvestingAmount(), mock.getInvestingAmount());

      Assertions.assertEquals(entity.getProduct().getProductId(), mock.getProduct().getProductId());
      Assertions.assertEquals(entity.getProduct().getTitle(), mock.getProduct().getTitle());
      Assertions.assertEquals(
          entity.getProduct().getTotalInvestingAmount(),
          mock.getProduct().getTotalInvestingAmount());
      Assertions.assertEquals(
          entity.getProduct().getCurrentInvestingAmount(),
          mock.getProduct().getCurrentInvestingAmount());
      Assertions.assertEquals(entity.getProduct().getStartedAt(), mock.getProduct().getStartedAt());
      Assertions.assertEquals(
          entity.getProduct().getFinishedAt(), mock.getProduct().getFinishedAt());
      Assertions.assertEquals(entity.getProduct().getState(), mock.getProduct().getState());
    }

    @Test
    @DisplayName("유저 id 별로 조회")
    void findById_UserId() {

      PageRequest pageable = PageRequest.of(0, 10);

      Page<InvestingProductInfo> entities =
          investingRepository.findById_UserId(1L, pageable, InvestingProductInfo.class);

      List<InvestingProductInfo> content = entities.getContent();

      Assertions.assertEquals(content.size(), 1);

      InvestingProductInfo entity = content.get(0);

      Product mockProduct = mock.getProduct();

      Assertions.assertEquals(entity.getProductId(), mockProduct.getProductId());
      Assertions.assertEquals(entity.getTitle(), mockProduct.getTitle());
      Assertions.assertEquals(
          entity.getTotalInvestingAmount(), mockProduct.getTotalInvestingAmount());
      Assertions.assertEquals(entity.getInvestingAmount(), mock.getInvestingAmount());
      Assertions.assertEquals(entity.getInvestingDate(), mock.getCreatedDate());
    }
  }
}
