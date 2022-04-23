package com.example.demo.mock;

import com.example.demo.domain.Product;
import com.example.demo.enums.StateType;
import com.example.demo.service.dto.ProductInfo;
import com.example.demo.service.dto.ProductInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductMock {

  private static final Long DEFAULT_ID = 1L;

  private static final String DEFAULT_TITLE = "test...";

  private static final Integer DEFAULT_AMOUNT = 1000;

  private static final Integer DEFAULT_CURRENT_AMOUNT = 0;

  private static final StateType DEFAULT_STATE = StateType.PROGRESS;

  public static Optional<Product> createdDefaultMockOptional() {
    return Optional.of(
        Product.builder()
            .productId(DEFAULT_ID)
            .title(DEFAULT_TITLE)
            .totalInvestingAmount(DEFAULT_AMOUNT)
            .currentInvestingAmount(DEFAULT_CURRENT_AMOUNT)
            .state(DEFAULT_STATE)
            .startedAt(createdDate("2020-01-01"))
            .finishedAt(createdDate("2020-01-20"))
            .build());
  }

  public static Product createdDefaultMock() {
    return Product.builder()
        .productId(DEFAULT_ID)
        .title(DEFAULT_TITLE)
        .totalInvestingAmount(DEFAULT_AMOUNT)
        .currentInvestingAmount(DEFAULT_CURRENT_AMOUNT)
        .state(DEFAULT_STATE)
        .startedAt(createdDate("2020-01-01"))
        .finishedAt(createdDate("2020-01-20"))
        .build();
  }

  public static List<Product> createDefaultMockList() {

    return List.of(
        Product.builder()
            .productId(DEFAULT_ID)
            .title(DEFAULT_TITLE)
            .totalInvestingAmount(DEFAULT_AMOUNT)
            .currentInvestingAmount(DEFAULT_CURRENT_AMOUNT)
            .state(DEFAULT_STATE)
            .startedAt(createdDate("2020-01-01"))
            .finishedAt(createdDate("2020-01-20"))
            .build(),
        Product.builder()
            .productId(2L)
            .title(DEFAULT_TITLE)
            .totalInvestingAmount(DEFAULT_AMOUNT)
            .currentInvestingAmount(DEFAULT_CURRENT_AMOUNT)
            .state(DEFAULT_STATE)
            .startedAt(new Date())
            .finishedAt(new Date())
            .build());
  }

  public static Date createdDate(String from) {
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
      return transFormat.parse(from);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String createdDate(Date from) {

    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return transFormat.format(from);
  }

  public static ProductInfoDTO createdProductInfoDTO() {
    return ProductInfoDTO.builder()
        .startedAtStr("2020-01-01")
        .finishedAtStr("2020-01-02")
        .page(0)
        .size(10)
        .build();
  }

  public static List<Product> createdAllSetProductList() {
    List<Product> list = ProductMock.createDefaultMockList();

    list.forEach(
        mock -> {
          mock.getInvestingSet()
              .addAll(
                  InvestingMock.createDefaultMockList(mock, mock.getProductId() == 1L ? 10 : 7));
        });

    return list;
  }

  public static Page<ProductInfo> cratedProductInfoToPage() {
    PageRequest pageable = PageRequest.of(0, 10);
    List<ProductInfo> list =
        createdAllSetProductList().stream()
            .map(ProductMock::convertOf)
            .collect(Collectors.toList());
    return new PageImpl<>(list, pageable, 2);
  }

  public static ProductInfo convertOf(Product product) {
    return new ProductInfo().setProductInfo(product);
  }
}
