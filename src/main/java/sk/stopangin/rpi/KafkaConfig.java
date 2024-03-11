package sk.stopangin.rpi;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import sk.stopangin.rpi.sensor.MeasurementDto;

@Configuration
public class KafkaConfig {

  @Bean
  public ProducerFactory<String, MeasurementDto> measurementProducerFactory(
      @Value("${rpi.kafka.server}") String kafkaBootstrapServer) {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean(name = "measurementKafkaTemplate")
  public KafkaTemplate<String, MeasurementDto> measurementKafkaTemplate(
      @Value("${rpi.kafka.server}") String kafkaBootstrapServer) {
    return new KafkaTemplate<>(measurementProducerFactory(kafkaBootstrapServer));
  }
}
