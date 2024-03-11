package sk.stopangin.rpi;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BME280Data {

  private final float temperature;
  private final float relativeHumidity;
  private final float pressure;
}
