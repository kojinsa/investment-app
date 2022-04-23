package com.example.demo.service.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public abstract class AbstractPage {

  @NotNull private final Integer page;

  @NotNull private final Integer size;

  protected AbstractPage(Integer page, Integer size) {
    this.page = page;
    this.size = size;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getSize() {
    return size;
  }

  public Pageable getPageRequest() {
    return PageRequest.of(this.page, this.size);
  }
}
