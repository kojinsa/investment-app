package com.example.demo.repository.support;

import com.example.demo.domain.Product;
import com.example.demo.service.dto.ProductInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.domain.QInvesting.investing;
import static com.example.demo.domain.QProduct.product;
import static com.querydsl.core.group.GroupBy.groupBy;

public class ProductCustomRepositoryImpl extends QuerydslRepositorySupport
    implements ProductCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public ProductCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Product.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Page<ProductInfo> findByStartedAtGreaterThanEqualAndFinishedAtLessThan(
      Date startedAt, Date finishedAt, Pageable pageable) {

    JPAQuery<Product> query =
        jpaQueryFactory
            .selectFrom(product)
            .leftJoin(product.investingSet, investing)
            .where(product.startedAt.goe(startedAt).and(product.finishedAt.lt(finishedAt)));

    List<ProductInfo> result =
        query
            .transform(
                groupBy(product.productId)
                    .list(
                        Projections.fields(
                            ProductInfo.class,
                            product.productId,
                            product.title,
                            product.totalInvestingAmount,
                            product.currentInvestingAmount,
                            product.startedAt,
                            product.finishedAt,
                            product.state,
                            Expressions.asNumber(investing.id.userId.count()).as("memberCount"))))
            .stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .collect(Collectors.toList());

    long total = query.fetchCount();

    return new PageImpl<>(result, pageable, total);
  }
}
