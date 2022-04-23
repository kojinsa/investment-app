package com.example.demo.service;

import com.example.demo.config.exception.NoDataException;
import com.example.demo.domain.Product;
import com.example.demo.enums.MsgType;
import com.example.demo.enums.StateType;
import com.example.demo.repository.InvestingRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.InvestProductDTO;
import com.example.demo.service.dto.InvestingProductInfo;
import com.example.demo.service.dto.InvestingProductInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestingServiceImpl implements InvestingService {

  private final InvestingRepository investingRepository;

  private final ProductRepository productRepository;

  @Override
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
  public StateType investProduct(InvestProductDTO dto, Long userId) {

    // 상품 조회
    Product product =
        productRepository
            .findById(dto.getProductId())
            .orElseThrow(() -> new NoDataException(MsgType.NoProductData));

    // 진행 중인 투자 상품일 경우
    if (StateType.PROGRESS.equals(product.getState())) {

      // 내 투자 정보 저장
      investingRepository.save(dto.toEntity(userId, product));

      Integer totalAmount = product.getTotalInvestingAmount();

      // 현재의 값 증가
      product.increaseCurrentAmount(dto.getAmount());

      Integer currentAmount = product.getCurrentInvestingAmount();

      // 현재 값과 총 값 비교
      if (currentAmount >= totalAmount) {
        // 솔드 아웃
        product.soldOut();
        return StateType.SOLDOUT;
      }

      // 진행중
      return StateType.PROGRESS;
    }

    return StateType.SOLDOUT;
  }

  @Override
  public Page<InvestingProductInfo> getInvestingProductList(
      InvestingProductInfoDTO dto, Long userId) {
    return investingRepository.findById_UserId(
        userId, dto.getPageRequest(), InvestingProductInfo.class);
  }
}
