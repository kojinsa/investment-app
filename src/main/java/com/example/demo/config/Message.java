package com.example.demo.config;

import lombok.*;

public class Message {

  @Builder
  @Getter
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Error {
    private String code;

    private String message;

    private String detailMessage;
  }
}
