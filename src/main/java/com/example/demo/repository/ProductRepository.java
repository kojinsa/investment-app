package com.example.demo.repository;

import com.example.demo.domain.Product;
import com.example.demo.repository.support.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {

  @Override
  Optional<Product> findById(Long aLong);
}
