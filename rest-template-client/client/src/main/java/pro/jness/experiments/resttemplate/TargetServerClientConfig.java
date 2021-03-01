package pro.jness.experiments.resttemplate;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

@Data
@Builder
public class TargetServerClientConfig {
    private String baseUrl;
    private RestTemplate restTemplate;
}
