package pro.jness.experiments.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class DefaultTargetServerClient implements TargetServerClient {
    private final String baseUrl;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<PingPongResp> ping() {
        return restTemplate.getForEntity(baseUrl + "/ping", PingPongResp.class);
    }
}
