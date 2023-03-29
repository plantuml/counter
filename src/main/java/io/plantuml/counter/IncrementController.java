package io.plantuml.counter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncrementController {

    @PostMapping(value = "/v2-increment", produces = "application/json")
    public String increment(@RequestBody String data) {
        CounterApplication.count.increment(System.currentTimeMillis());
        System.err.println("IncrementController::increment " + data);
        // {"status":"ok"}
        return "{\"status\":\"ok\"}";
    }
}
