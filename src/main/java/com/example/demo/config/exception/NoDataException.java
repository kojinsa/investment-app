package com.example.demo.config.exception;

import com.example.demo.enums.MsgType;

public class NoDataException extends BaseException {

  public NoDataException(MsgType msgType) {
    super(msgType);
  }
}
