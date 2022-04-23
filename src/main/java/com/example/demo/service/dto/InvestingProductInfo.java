package com.example.demo.service.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface InvestingProductInfo {

  // 상품 ID
  @Value("#{target.product.productId}")
  Long getProductId();

  // 상품 제목
  @Value("#{target.product.title}")
  String getTitle();

  // 총 모집금액
  @Value("#{target.product.totalInvestingAmount}")
  Integer getTotalInvestingAmount();

  // 나의 투자금액
  Integer getInvestingAmount();

  // 내 투자일시
  @Value("#{target.createdDate}")
  Date getInvestingDate();
}
