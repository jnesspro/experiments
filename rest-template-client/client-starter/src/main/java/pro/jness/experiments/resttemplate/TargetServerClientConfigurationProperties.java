package pro.jness.experiments.resttemplate;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("test-server-client")
@Data
public class TargetServerClientConfigurationProperties {
    private String baseUrl;
}
