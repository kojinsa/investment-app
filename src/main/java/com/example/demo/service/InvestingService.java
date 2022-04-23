package com.example.demo.service;

import com.example.demo.enums.StateType;
import com.example.demo.service.dto.InvestProductDTO;
import com.example.demo.service.dto.InvestingProductInfo;
import com.example.demo.service.dto.InvestingProductInfoDTO;
import org.springframework.data.domain.Page;

public interface InvestingService {

  /**
   * 투자하기 로직
   *
   * @param dto InvestProductDTO
   * @param userId Long
   * @return StateType
   */
  StateType investProduct(InvestProductDTO dto, Long userId);

  /**
   * 나의 투자상품 조회
   *
   * @param dto InvestingProductInfoDTO
   * @param userId Long
   * @return Page<InvestingProductInfo>
   */
  Page<InvestingProductInfo> getInvestingProductList(InvestingProductInfoDTO dto, Long userId);
}
