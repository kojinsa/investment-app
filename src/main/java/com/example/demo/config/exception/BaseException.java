package com.example.demo.config.exception;

import com.example.demo.enums.MsgType;

public class BaseException extends RuntimeException {

  private final MsgType msgType;

  public BaseException(MsgType msgType) {
    super(msgType.getMessage());
    this.msgType = msgType;
  }

  public MsgType getMsgType() {
    return msgType;
  }
}
