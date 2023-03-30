package io.plantuml.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadgePeakController {

    @GetMapping(value = "/v2-peak-minute", produces = "application/json")
    public String peak() {
        final int nb = 42;
        final String label = "peak rate";
        final String message = "" + nb + " diag. per minute";
        final String color = Badge.COLOR_IMPORTANT;
        return new Badge(label, message, color).toJSonString();
    }
}
