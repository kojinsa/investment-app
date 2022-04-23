package com.example.demo.repository;

import com.example.demo.domain.Investing;
import com.example.demo.domain.InvestingKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestingRepository extends JpaRepository<Investing, InvestingKey> {

  @Override
  @EntityGraph(attributePaths = {"product"})
  Optional<Investing> findById(InvestingKey investingKey);

  @EntityGraph(attributePaths = {"product"})
  <T> Page<T> findById_UserId(Long userId, Pageable pageable, Class<T> type);
}
