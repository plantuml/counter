package io.plantuml.counter;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.containsString;

@SpringBootTest(classes = CounterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CounterApplicationTests {

    @LocalServerPort
    private int port;


    @Test
    void ping() {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);

        final ResponseEntity<String> result = restTemplate.getForEntity(createURLWithPort("/ping"), String.class);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        MatcherAssert.assertThat(result.getBody(), containsString("v4"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
