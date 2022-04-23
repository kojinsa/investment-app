package com.example.demo.mock;

import com.example.demo.domain.Investing;
import com.example.demo.domain.InvestingKey;
import com.example.demo.domain.Product;
import com.example.demo.service.dto.InvestProductDTO;
import com.example.demo.service.dto.InvestingProductInfo;
import com.example.demo.service.dto.InvestingProductInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InvestingMock {

  private static final Long DEFAULT_USER_ID = 1L;

  private static final Integer DEFAULT_AMOUNT = 100;

  public static Investing createdDefaultMock(Product product) {
    InvestingKey id =
        InvestingKey.builder().productId(product.getProductId()).userId(DEFAULT_USER_ID).build();
    return Investing.builder().id(id).investingAmount(DEFAULT_AMOUNT).product(product).build();
  }

  public static List<Investing> createDefaultMockList(Product product, Integer size) {
    return IntStream.range(0, size)
        .mapToObj(
            num -> {
              InvestingKey id =
                  InvestingKey.builder()
                      .productId(product.getProductId())
                      .userId((long) num)
                      .build();
              return Investing.builder()
                  .id(id)
                  .investingAmount(DEFAULT_AMOUNT)
                  .product(product)
                  .build();
            })
        .collect(Collectors.toList());
  }

  public static InvestProductDTO createdInvestProductDTO(Integer amount) {
    return InvestProductDTO.builder().productId(1L).amount(amount).build();
  }

  public static List<InvestingProductInfo> createDefaultMockList() {
    return ProductMock.createdAllSetProductList().stream()
        .map(
            product ->
                product.getInvestingSet().stream()
                    .map(investing -> new InvestingProductInfoMock(product, investing))
                    .collect(Collectors.toList()))
        .flatMap(Collection::parallelStream)
        .collect(Collectors.toList());
  }

  public static Page<InvestingProductInfo> createdPageMockList() {
    List<InvestingProductInfo> list = createDefaultMockList();
    PageRequest pageabale = PageRequest.of(0, 10);
    return new PageImpl<>(list, pageabale, list.size());
  }

  public static InvestingProductInfoDTO createdInvestingProductInfoDTO() {
    return InvestingProductInfoDTO.builder().page(0).size(10).build();
  }

  static class InvestingProductInfoMock implements InvestingProductInfo {

    private final Long productId;

    private final String title;

    private final Integer totalInvestingAmount;

    private final Integer investingAmount;

    private final Date investingDate;

    public InvestingProductInfoMock(Product product, Investing investing) {
      this.productId = product.getProductId();
      this.title = product.getTitle();
      this.totalInvestingAmount = product.getTotalInvestingAmount();
      this.investingAmount = investing.getInvestingAmount();
      this.investingDate = investing.getCreatedDate();
    }

    @Override
    public Long getProductId() {
      return this.productId;
    }

    @Override
    public String getTitle() {
      return this.title;
    }

    @Override
    public Integer getTotalInvestingAmount() {
      return this.totalInvestingAmount;
    }

    @Override
    public Integer getInvestingAmount() {
      return this.investingAmount;
    }

    @Override
    public Date getInvestingDate() {
      return this.investingDate;
    }
  }
}
