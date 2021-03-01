package pro.jness.experiments.resttemplate;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpResponse;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.model.HttpRequest.request;

public class TargetMockServer extends MockServerContainer {

    public TargetMockServer() {
        super(DockerImageName.parse("mockserver/mockserver:mockserver-5.11.2"));
    }

    public TargetMockServer(DockerImageName dockerImageName) {
        super(dockerImageName);
    }

    public void mockPing(HttpResponse response) {
        new MockServerClient(getHost(), getServerPort())
                .when(request()
                        .withPath("/ping"))
                .respond(response);
    }
}
