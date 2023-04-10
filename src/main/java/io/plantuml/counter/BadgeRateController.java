package io.plantuml.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class BadgeRateController {

    @GetMapping(value = "/v2-rate-minute", produces = "application/json")
    public String rate() {
        final long nb = CounterApplication.count.diagramsPerMinute();
        final String label = "current rate";
        final String message = "" + String.format(Locale.US, "%,d", nb) + " diag. per minute";
        final String color = Badge.COLOR_INFORMAL;
        return new Badge(label, message, color).toJSonString();
    }
}
