package pro.jness.experiments.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(TargetServerClientConfigurationProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "test-server-client.base-url")
public class TargetServerClientAutoConfiguration {

    private final TargetServerClientConfigurationProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public TargetServerClientConfig targetServerClientConfig() {
        return TargetServerClientConfig.builder()
                .baseUrl(properties.getBaseUrl())
                .restTemplate(new RestTemplate())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultTargetServerClient targetServerClient(TargetServerClientConfig config) {
        return new DefaultTargetServerClient(
                config.getBaseUrl(),
                config.getRestTemplate()
        );
    }
}
