package com.example.demo.enums;

public enum MsgType {
  InternalServerError("M000", "InternalServerError"),
  NoStateTypeData("M001", "state type is not null"),
  NoProductData("M002", "no product data"),
  NoContextUserId("M003", "no context user id not setting");

  private final String code;

  private final String message;

  MsgType(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getCode() {
    return code;
  }
}
