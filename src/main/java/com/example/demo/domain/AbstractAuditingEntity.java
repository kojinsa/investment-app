package com.example.demo.domain;

import lombok.Getter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity {

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedDate;
}
