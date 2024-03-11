package sk.stopangin.rpi;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import one.microproject.rpi.hardware.gpio.sensors.BME280;
import one.microproject.rpi.hardware.gpio.sensors.BME280Builder;
import one.microproject.rpi.hardware.gpio.sensors.impl.BME280Impl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sk.stopangin.rpi.properties.MetadataProperties;
import sk.stopangin.rpi.sensor.MeasurementDto;
import sk.stopangin.rpi.sensor.SensorDataDto;
import sk.stopangin.rpi.sensor.TemperatureDataDto;
import sk.stopangin.rpi.sensor.TemperatureUnit;

@Slf4j
@Component
public class BME280DataReader implements DataReader {

  private final DataPublisher<MeasurementDto> publisher;
  private final int sendingDelayMs;

  private final MetadataProperties metadataProperties;
  private boolean isStopped;


  public BME280DataReader(DataPublisher<MeasurementDto> publisher,
      @Value("${rpi.sender.sendingDelayMs}") int sendingDelayMs,
      MetadataProperties metadataProperties) {
    this.publisher = publisher;
    this.sendingDelayMs = sendingDelayMs;
    this.metadataProperties = metadataProperties;
  }

  @Override
  public void readData() {
    Context context = Pi4J.newAutoContext();
    context.provider(LinuxFsI2CProvider.class);
    try (BME280 bme280 = BME280Builder.get().context(context).build()) {
      while (!isStopped) {
        MeasurementDto measurement = createMeasurement(bme280);
        publisher.publish(measurement);
        sleepSafe();
      }
    } catch (Exception e) {
      throw new DataReaderException(e);
    }
  }

  private MeasurementDto createMeasurement(BME280 bme280) {
//float pres = data.getPressure() / 1000;
//new BME280Data(data.getTemperature(), data.getRelativeHumidity(), pres));

    BME280Impl.Data data = bme280.getSensorValues();
    UUID measurementId = UUID.randomUUID();
    List<SensorDataDto<?>> sensorData = new ArrayList<>();
    SensorDataDto<Double> temperature = new TemperatureDataDto(measurementId, TemperatureUnit.C,
        (double) data.getTemperature());
    sensorData.add(temperature);
    return new MeasurementDto(metadataProperties.getCompanyId(), sensorData);
  }


  @Override
  public void stop() {
    isStopped = true;
  }

  private void sleepSafe() {
    try {
      Thread.sleep(sendingDelayMs);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
