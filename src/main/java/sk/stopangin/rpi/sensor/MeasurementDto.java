package sk.stopangin.rpi.sensor;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeasurementDto {

  private UUID companyId;
  private List<SensorDataDto<?>> sensorDataDtos;

}
