package sk.stopangin.rpi.properties;

import java.util.UUID;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rpi")
public class MetadataProperties {

  private UUID companyId;
}
