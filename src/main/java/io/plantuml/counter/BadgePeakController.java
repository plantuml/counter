package io.plantuml.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class BadgePeakController {

    @GetMapping(value = "/v2-peak-minute", produces = "application/json")
    public String peak() {
        final long nb = CounterApplication.count.peakPerMinute();
        final String label = "peak rate";
        final String message = "" + String.format(Locale.US, "%,d", nb) + " diag. per minute";
        final String color = Badge.COLOR_IMPORTANT;
        return new Badge(label, message, color).toJSonString();
    }
}
