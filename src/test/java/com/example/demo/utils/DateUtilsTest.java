package com.example.demo.utils;

import com.example.demo.mock.ProductMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilsTest {

  @Test
  @DisplayName("date type 하루 추가 테스트 케이스")
  void plusDate() {

    final Date mock = ProductMock.createdDate("2020-01-02");

    Date date = ProductMock.createdDate("2020-01-01");

    Calendar c = Calendar.getInstance();

    c.setTime(date);

    c.add(Calendar.DATE, 1);

    date = c.getTime();

    Assertions.assertEquals(date, mock);
  }

  @Test
  @DisplayName("날짜 변경 utils 테스트")
  void createdDateOf() throws Exception {

    Date mock = ProductMock.createdDate("2020-01-01");

    String from = "2020-01-01";

    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

    Date date = transFormat.parse(from);

    Assertions.assertEquals(mock, date);
  }
}
