package com.example.demo.config;

import com.example.demo.config.exception.NoDataException;
import com.example.demo.enums.MsgType;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionHandler {

  /**
   * Exception 헨들러
   *
   * @param ex - Exception
   * @param response HttpServletResponse
   * @return ResponseEntity<Message.Error>
   */
  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Message.Error> handler(Exception ex, HttpServletResponse response) {
    MsgType errorType = MsgType.InternalServerError;
    response.reset();
    Message.Error message = setMessage(ex, errorType);
    return new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * NoDataException 핸들러
   *
   * @param ex - NoDataException
   * @param response - HttpServletResponse
   * @return ResponseEntity<Message.Error>
   */
  @ResponseBody
  @ExceptionHandler(NoDataException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Message.Error> handler(NoDataException ex, HttpServletResponse response) {
    MsgType errorType = ex.getMsgType();
    response.reset();
    Message.Error message = setMessage(ex, errorType);
    return new ResponseEntity<Message.Error>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * 에러 메시지를 만들어 주는 메소드
   *
   * @param ex - Exception
   * @param errorType - ErrorType
   * @return Message.Error
   */
  private Message.Error setMessage(Exception ex, MsgType errorType) {
    ex.printStackTrace();

    String detailMessage = ExceptionUtils.getStackTrace(ex);

    return Message.Error.builder()
        .code(errorType.getCode())
        .message(errorType.getMessage())
        .detailMessage(detailMessage)
        .build();
  }
}
