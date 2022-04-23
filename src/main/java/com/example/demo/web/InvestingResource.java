package com.example.demo.web;

import com.example.demo.enums.StateType;
import com.example.demo.service.InvestingService;
import com.example.demo.service.ProductService;
import com.example.demo.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InvestingResource {

  private final ProductService productService;

  private final InvestingService investingService;

  private final String USER_ID = "X-USER-ID";

  /*전체 투자 상품 조회 API*/
  @GetMapping("/products")
  public ResponseEntity<Page<ProductInfo>> showProductList(@Valid ProductInfoDTO dto) {
    Page<ProductInfo> body = productService.getProductList(dto);
    return ResponseEntity.ok(body);
  }

  /*투자하기 API*/
  @PutMapping("/investing")
  public ResponseEntity<StateType> investProduct(
      @RequestHeader(USER_ID) String userIdStr, @RequestBody @Valid InvestProductDTO dto) {
    Long userId = Long.valueOf(userIdStr);
    StateType stateType = investingService.investProduct(dto, userId);
    return ResponseEntity.ok(stateType);
  }

  /*나의 투자상품 조회 API*/
  @GetMapping("/investing-products")
  public ResponseEntity<Page<InvestingProductInfo>> showInvestingProductList(
      @RequestHeader(USER_ID) String userIdStr, @Valid InvestingProductInfoDTO dto) {
    Long userId = Long.valueOf(userIdStr);
    Page<InvestingProductInfo> body = investingService.getInvestingProductList(dto, userId);
    return ResponseEntity.ok(body);
  }
}
