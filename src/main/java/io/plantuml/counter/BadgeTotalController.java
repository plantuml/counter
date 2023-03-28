package io.plantuml.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadgeTotalController {

    @GetMapping(value = "/v2-total", produces = "application/json")
    public String ping() {
        final int nb = 0;
        final String label = "online diagrams";
        final String message = "" + nb;
        final String color = Badge.COLOR_INFORMAL;
        return new Badge(label, message, color).toJSonString();
    }
}
