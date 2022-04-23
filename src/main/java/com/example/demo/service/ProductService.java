package com.example.demo.service;

import com.example.demo.service.dto.ProductInfo;
import com.example.demo.service.dto.ProductInfoDTO;
import org.springframework.data.domain.Page;

public interface ProductService {

  /**
   * 나의 투자상품 조회
   *
   * @param dto ProductInfoDTO
   * @return ProductInfoDTO
   */
  Page<ProductInfo> getProductList(ProductInfoDTO dto);
}
