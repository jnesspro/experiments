package pro.jness.experiments.resttemplate;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ping")
public class PingController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PingPongResp ping() {
        return new PingPongResp("pong");
    }
}
