package pro.jness.experiments.resttemplate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = {
        TargetServerClientAutoConfiguration.class,
        AutoConfigurationTestConfiguration.class
}, properties = {
        "test-server-client.base-url=https://server"
})
@Import(CustomAutoConfigurationTest.CustomAutoConfigurationTestConfiguration.class)
public class CustomAutoConfigurationTest {

    @TestConfiguration
    public static class CustomAutoConfigurationTestConfiguration {

        public static final String TEST_BASE_URL = "https://test";

        @Bean
        public TargetServerClientConfig targetServerClientConfig() {
            return TargetServerClientConfig.builder()
                    .baseUrl(TEST_BASE_URL)
                    .restTemplate(new RestTemplate())
                    .build();
        }
    }

    @Autowired
    private ApplicationContext context;

    @Test
    public void beanExists() {
        TargetServerClientConfig bean = context.getBean(TargetServerClientConfig.class);
        Assertions.assertEquals(CustomAutoConfigurationTestConfiguration.TEST_BASE_URL, bean.getBaseUrl());
    }
}
