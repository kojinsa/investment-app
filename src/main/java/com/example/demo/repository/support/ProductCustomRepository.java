package com.example.demo.repository.support;

import com.example.demo.service.dto.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ProductCustomRepository {

  Page<ProductInfo> findByStartedAtGreaterThanEqualAndFinishedAtLessThan(
      Date startedAt, Date finishedAt, Pageable pageable);
}
