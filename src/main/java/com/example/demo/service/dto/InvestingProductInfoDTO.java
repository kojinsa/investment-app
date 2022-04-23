package com.example.demo.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InvestingProductInfoDTO extends AbstractPage {

  @Builder
  private InvestingProductInfoDTO(Integer page, Integer size) {
    super(page, size);
  }
}
