package sk.stopangin.rpi.sensor;

import java.util.UUID;

public class TemperatureDataDto extends SensorDataDto<Double> {

  public TemperatureDataDto(UUID id, TemperatureUnit unit, Double value) {
    super(id, unit, value);
  }

}
