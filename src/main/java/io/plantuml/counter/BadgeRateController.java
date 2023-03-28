package io.plantuml.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadgeRateController {

    @GetMapping(value = "/v2-rate", produces = "application/json")
    public String ping() {
        final int nb = 42;
        final String label = "current rate";
        final String message = "" + nb + " diag. per minute";
        final String color = Badge.COLOR_INFORMAL;
        return new Badge(label, message, color).toJSonString();
    }
}