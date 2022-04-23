package com.example.demo.domain;

import com.example.demo.enums.StateType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Table
@Entity
@Builder
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OptimisticLocking(type = OptimisticLockType.DIRTY)
public class Product extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Integer totalInvestingAmount;

  @Column(nullable = false)
  private Integer currentInvestingAmount;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date startedAt;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date finishedAt;

  @Column(nullable = false)
  private StateType state;

  @Builder.Default
  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REMOVE},
      mappedBy = "product")
  @MapKey(name = "id")
  private Set<Investing> investingSet = new HashSet<>();

  @Version private Integer version;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(productId, product.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId);
  }

  @PrePersist
  void prePersist() {

    this.state = StateType.PROGRESS;

    if (ObjectUtils.isEmpty(this.totalInvestingAmount)) {
      this.totalInvestingAmount = 0;
    }

    if (ObjectUtils.isEmpty(this.currentInvestingAmount)) {
      this.currentInvestingAmount = 0;
    }
  }

  @Transient
  public void increaseCurrentAmount(Integer amount) {
    this.currentInvestingAmount += amount;
  }

  @Transient
  public void soldOut() {
    this.state = StateType.SOLDOUT;
  }
}
