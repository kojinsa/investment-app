package com.example.demo.mock;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MockUtils {

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
