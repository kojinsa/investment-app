package com.example.demo.domain;

import com.example.demo.config.exception.NoDataException;
import com.example.demo.enums.MsgType;
import com.example.demo.enums.StateType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class StateTypeConvert implements AttributeConverter<StateType, String> {

  @Override
  public String convertToDatabaseColumn(StateType attribute) {
    return Optional.ofNullable(attribute)
        .orElseThrow(() -> new NoDataException(MsgType.NoStateTypeData))
        .name();
  }

  @Override
  public StateType convertToEntityAttribute(String dbData) {
    return StateType.valueOf(
        Optional.ofNullable(dbData)
            .orElseThrow(() -> new NoDataException(MsgType.NoStateTypeData)));
  }
}
