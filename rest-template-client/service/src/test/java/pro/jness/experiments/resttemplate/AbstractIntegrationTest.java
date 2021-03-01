package pro.jness.experiments.resttemplate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AbstractIntegrationTest {

    private TargetServerClient client;
    @Container
    protected TargetMockServer targetMockServer = new TargetMockServer();

    @LocalServerPort
    int localServerPort;

    @BeforeEach
    public void setUpClient() {
        client = new DefaultTargetServerClient(
                "http://localhost:" + localServerPort,
                new RestTemplate());
    }

    public TargetServerClient getClient() {
        return client;
    }

    public TargetMockServer getTargetMockServer() {
        return targetMockServer;
    }
}