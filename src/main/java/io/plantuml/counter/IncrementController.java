package io.plantuml.counter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncrementController {

    public static SCounter count = new SCounter();

    @PostMapping(value = "/v2-increment", produces = "application/json")
    public String increment() {
        return "ok";
    }
}
