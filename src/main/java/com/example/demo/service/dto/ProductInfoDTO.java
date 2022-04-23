package com.example.demo.service.dto;

import com.example.demo.utils.DateUtils;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class ProductInfoDTO extends AbstractPage {

  @NotNull
  private final String startedAtStr;

  @NotNull
  private final String finishedAtStr;

  @Builder
  private ProductInfoDTO(Integer page, Integer size, String startedAtStr, String finishedAtStr) {
    super(page, size);
    this.startedAtStr = startedAtStr;
    this.finishedAtStr = finishedAtStr;
  }

  public Date getFinishedAt() {
    Date date = DateUtils.createdDateOf(this.finishedAtStr);
    return DateUtils.plusDate(date, 1);
  }

  public Date getStartedAt() {
    return DateUtils.createdDateOf(this.startedAtStr);
  }
}
