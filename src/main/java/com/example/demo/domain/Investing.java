package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Builder
@Entity
@Table(name = "investing_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Investing extends AbstractAuditingEntity {

  @EmbeddedId private InvestingKey id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("productId")
  @JoinColumn(
      name = "productId",
      referencedColumnName = "productId",
      insertable = false,
      updatable = false)
  private Product product;

  @Column(nullable = false)
  private Integer investingAmount;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Investing investing = (Investing) o;
    return Objects.equals(id, investing.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
