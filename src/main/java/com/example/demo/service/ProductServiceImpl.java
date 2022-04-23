package com.example.demo.service;

import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductInfo;
import com.example.demo.service.dto.ProductInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Page<ProductInfo> getProductList(ProductInfoDTO dto) {

    Date startedAt = dto.getStartedAt();

    Date finishedAt = dto.getFinishedAt();

    Pageable pageable = dto.getPageRequest();

    return productRepository.findByStartedAtGreaterThanEqualAndFinishedAtLessThan(
        startedAt, finishedAt, pageable);
  }
}
