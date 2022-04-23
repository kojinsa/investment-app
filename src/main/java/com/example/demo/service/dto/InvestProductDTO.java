package com.example.demo.service.dto;

import com.example.demo.domain.Investing;
import com.example.demo.domain.InvestingKey;
import com.example.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvestProductDTO {

  // 상품 ID
  @NotNull private Long productId;

  // 투자 금액
  @NotNull private Integer amount;

  public Investing toEntity(Long userId, Product product) {
    return Investing.builder()
        .id(getId(userId))
        .investingAmount(this.amount)
        .product(product)
        .build();
  }

  private InvestingKey getId(Long userId) {
    return InvestingKey.builder().productId(this.productId).userId(userId).build();
  }
}
