package pro.jness.experiments.resttemplate;

import org.springframework.http.ResponseEntity;

public interface TargetServerClient {
    ResponseEntity<PingPongResp> ping();
}
