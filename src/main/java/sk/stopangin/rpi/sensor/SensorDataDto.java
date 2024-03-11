package sk.stopangin.rpi.sensor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TemperatureDataDto.class, name = "temperature")
})
public abstract class SensorDataDto<T> {

  private UUID id;

  private Unit unit;
  private T value;

}