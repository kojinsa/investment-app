package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

  public static Date createdDateOf(String from) {

    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
      return transFormat.parse(from);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Date plusDate(Date date, int num) {

    Calendar c = Calendar.getInstance();

    c.setTime(date);

    c.add(Calendar.DATE, num);

    return c.getTime();
  }
}
