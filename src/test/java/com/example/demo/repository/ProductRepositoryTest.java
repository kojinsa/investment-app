package com.example.demo.repository;

import com.example.demo.config.JpaConfiguration;
import com.example.demo.domain.Product;
import com.example.demo.mock.InvestingMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.service.dto.ProductInfo;
import com.example.demo.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(JpaConfiguration.class)
class ProductRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @Autowired private InvestingRepository investingRepository;

  @Test
  @DisplayName("저장 로직 테스트 케이스")
  void save() {

    Product mock = ProductMock.createdDefaultMock();

    Product entity = productRepository.save(mock);

    Assertions.assertThat(entity).isEqualTo(mock);

    org.junit.jupiter.api.Assertions.assertEquals(entity.getProductId(), mock.getProductId());
    org.junit.jupiter.api.Assertions.assertEquals(entity.getTitle(), mock.getTitle());
    org.junit.jupiter.api.Assertions.assertEquals(
        entity.getTotalInvestingAmount(), mock.getTotalInvestingAmount());
    org.junit.jupiter.api.Assertions.assertEquals(entity.getStartedAt(), mock.getStartedAt());
    org.junit.jupiter.api.Assertions.assertEquals(entity.getFinishedAt(), mock.getFinishedAt());
    org.junit.jupiter.api.Assertions.assertEquals(entity.getState(), mock.getState());
    org.junit.jupiter.api.Assertions.assertEquals(
        entity.getCurrentInvestingAmount(), mock.getCurrentInvestingAmount());
  }

  @Nested
  @DisplayName("조히")
  class Select {

    List<Product> mockList;

    @BeforeEach
    void init() {

      mockList = productRepository.saveAll(ProductMock.createDefaultMockList());

      mockList.forEach(
          mock -> {
            investingRepository.saveAll(
                InvestingMock.createDefaultMockList(mock, mock.getProductId() == 1L ? 10 : 7));
          });

      investingRepository.flush();
      productRepository.flush();
    }

    @Test
    @DisplayName("시작 시간 & 끝난는 시간 페이징 처리")
    void findByStartedAtGreaterThanEqualAndFinishedAtLessThan() {

      PageRequest pageable = PageRequest.of(0, 10);

      Date startedAt = ProductMock.createdDate("2020-01-01");

      // enddate + 1
      Date finishedAt = DateUtils.plusDate(ProductMock.createdDate("2020-01-31"), 1);

      Page<ProductInfo> entities =
          productRepository.findByStartedAtGreaterThanEqualAndFinishedAtLessThan(
              startedAt, finishedAt, pageable);

      List<ProductInfo> content = entities.getContent();

      org.junit.jupiter.api.Assertions.assertEquals(content.size(), 1);

      content.forEach(
          entity -> {
            Product mock =
                mockList.stream()
                    .filter(value -> value.getProductId().equals(entity.getProductId()))
                    .findFirst()
                    .orElseThrow();

            org.junit.jupiter.api.Assertions.assertEquals(
                entity.getProductId(), mock.getProductId());
            org.junit.jupiter.api.Assertions.assertEquals(entity.getTitle(), mock.getTitle());
            org.junit.jupiter.api.Assertions.assertEquals(
                entity.getTotalInvestingAmount(), mock.getTotalInvestingAmount());
            org.junit.jupiter.api.Assertions.assertEquals(
                entity.getCurrentInvestingAmount(), mock.getCurrentInvestingAmount());
            org.junit.jupiter.api.Assertions.assertEquals(
                ProductMock.createdDate(entity.getStartedAt()),
                ProductMock.createdDate(mock.getStartedAt()));
            org.junit.jupiter.api.Assertions.assertEquals(
                ProductMock.createdDate(entity.getFinishedAt()),
                ProductMock.createdDate(mock.getFinishedAt()));
            org.junit.jupiter.api.Assertions.assertEquals(entity.getState(), mock.getState());
            // number checked
            org.junit.jupiter.api.Assertions.assertEquals(entity.getMemberCount(), 10);
          });
    }
  }
}
