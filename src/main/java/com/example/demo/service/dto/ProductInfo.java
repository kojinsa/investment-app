package com.example.demo.service.dto;

import com.example.demo.domain.Product;
import com.example.demo.enums.StateType;
import lombok.Getter;

import java.util.Date;

@Getter
public class ProductInfo {

  // 상품 ID
  private Long productId;

  // 상품 제목
  private String title;

  // 총 모집금액
  private Integer totalInvestingAmount;

  // 현재 모집금액
  private Integer currentInvestingAmount;

  // 상품 모집기간 시작 기간
  private Date startedAt;

  // 상품 모집기간 끝는 시간
  private Date finishedAt;

  // 투자모집상태(모집중, 모집 완료)
  private StateType state;

  // 투자자 수
  private Long memberCount;

  public ProductInfo setProductInfo(Product product) {
    this.productId = product.getProductId();
    this.title = product.getTitle();
    this.totalInvestingAmount = product.getTotalInvestingAmount();
    this.currentInvestingAmount = product.getCurrentInvestingAmount();
    this.startedAt = product.getStartedAt();
    this.finishedAt = product.getFinishedAt();
    this.state = product.getState();
    this.memberCount = Long.valueOf(product.getInvestingSet().size());
    return this;
  }
}
