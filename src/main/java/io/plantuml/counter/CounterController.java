package io.plantuml.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

    public static SCounter count = new SCounter();

    @PostMapping(value = "/counter", produces = "application/json")
    public String ping() {
        return "ok";
    }
}
