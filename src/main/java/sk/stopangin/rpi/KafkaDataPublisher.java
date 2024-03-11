package sk.stopangin.rpi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sk.stopangin.rpi.sensor.MeasurementDto;

@Slf4j
@Component
public class KafkaDataPublisher implements DataPublisher<MeasurementDto> {

  private final KafkaTemplate<String, MeasurementDto> measurementDtoKafkaTemplate;

  private final String topicName;

  public KafkaDataPublisher(@Qualifier("measurementKafkaTemplate")
  KafkaTemplate<String, MeasurementDto> measurementKafkaTemplate,
      @Value("${rpi.kafka.producer.topic}") String topicName) {
    this.measurementDtoKafkaTemplate = measurementKafkaTemplate;
    this.topicName = topicName;
  }

  @Override
  public void publish(MeasurementDto data) {

    measurementDtoKafkaTemplate.send(topicName, data).addCallback(result -> {
        }, ex -> log.warn(ex.getMessage(), ex)
    );

  }
}
