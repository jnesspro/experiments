package pro.jness.experiments.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class AutoConfigurationTestConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
