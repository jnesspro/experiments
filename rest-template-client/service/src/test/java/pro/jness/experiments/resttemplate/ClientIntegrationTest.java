package pro.jness.experiments.resttemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.*;

import java.util.Arrays;
import java.util.Collections;

public class ClientIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void pingPongTest() {
        ResponseEntity<PingPongResp> resp = getClient().ping();
        Assertions.assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    public void pingPongTest_wrongResponseType() {
        getTargetMockServer().mockPing(
                HttpResponse.response()
                        .withHeader("Content-Type", MediaType.APPLICATION_ATOM_XML_VALUE)
                        .withBody("<a>oops!</a>"));
        TargetServerClient client = new DefaultTargetServerClient(
                getTargetMockServer().getEndpoint(),
                new RestTemplate());
        Assertions.assertThrows(UnknownContentTypeException.class, client::ping);
    }

    @Test
    public void pingPongTest_404() {
        getTargetMockServer().mockPing(
                HttpResponse.response()
                        .withStatusCode(HttpStatus.NOT_FOUND.value()));
        TargetServerClient client = new DefaultTargetServerClient(
                getTargetMockServer().getEndpoint(),
                new RestTemplate());
        Assertions.assertThrows(HttpClientErrorException.NotFound.class, client::ping);
    }

    @Test
    public void pingPongTest_wrongResponseStructure() {
        getTargetMockServer().mockPing(
                HttpResponse.response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"f1\": \"v1\"}"));
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mc = new MappingJackson2HttpMessageConverter();
        mc.setObjectMapper(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true));
        restTemplate.setMessageConverters(Collections.singletonList(mc));
        TargetServerClient client = new DefaultTargetServerClient(
                getTargetMockServer().getEndpoint(),
                restTemplate);
        Assertions.assertThrows(RestClientException.class, client::ping);
    }

    @Test
    public void pingPongTest_invalidJson() {
        getTargetMockServer().mockPing(
                HttpResponse.response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"f1\", \"v1\"}"));
        TargetServerClient client = new DefaultTargetServerClient(
                getTargetMockServer().getEndpoint(),
                new RestTemplate());
        Assertions.assertThrows(RestClientException.class, client::ping);
    }

    @Test
    public void pingPongTest_redirect() {
        getTargetMockServer().mockPing(
                HttpResponse.response()
                        .withStatusCode(HttpStatus.FOUND.value())
                        .withHeader("Location", "https://localhost:" + RandomUtils.nextInt(1, 20000)));
        TargetServerClient client = new DefaultTargetServerClient(
                getTargetMockServer().getEndpoint(),
                new RestTemplate());
        Assertions.assertEquals(HttpStatus.FOUND, client.ping().getStatusCode());
    }
}
