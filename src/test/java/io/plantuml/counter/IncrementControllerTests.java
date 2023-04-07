package io.plantuml.counter;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest(classes = CounterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IncrementControllerTests {

    @LocalServerPort
    private int port;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/counter" + uri;
    }

    @Test
    void increment() {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);

        MatcherAssert.assertThat(CounterApplication.count.getTotal(), equalTo(0L));
        final ResponseEntity<String> result = restTemplate.postForEntity(createURLWithPort("/v2-increment"),
                "{\"nb\":1, \"who\":\"test\"}", String.class);
        MatcherAssert.assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));

        MatcherAssert.assertThat(CounterApplication.count.getTotal(), equalTo(1L));
        MatcherAssert.assertThat(result.getBody(), containsString("ok"));
    }

}
