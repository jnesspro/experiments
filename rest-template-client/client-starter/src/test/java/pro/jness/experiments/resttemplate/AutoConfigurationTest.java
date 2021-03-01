package pro.jness.experiments.resttemplate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = {
        TargetServerClientAutoConfiguration.class,
        AutoConfigurationTestConfiguration.class
}, properties = {
        "test-server-client.base-url=https://server"
})
public class AutoConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
    }

    @Test
    public void beanExists() {
        Assertions.assertNotNull(context.getBean(TargetServerClient.class));
    }
}
