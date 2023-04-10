package io.plantuml.counter;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class IncrementController {

    @PostMapping(value = "/v2-increment", produces = "application/json")
    public String increment(@RequestHeader Map<String, String> headers, @RequestBody String data) {
        System.err.println(headers);
        System.err.println("increment " + new Date() + " " + data);
        JSONObject obj = new JSONObject(data);
        if (obj.has("nb") && obj.has("who")) {
            final int nb = obj.getInt("nb");
            if (obj.has("min") && obj.has("max")) {
                final long min = obj.getLong("min");
                final long max = obj.getLong("max");
                for (int i = 0; i < nb; i++) {
                    final long when = min + (max - min) * i / nb;
                    CounterApplication.count.increment(when);
                }

            } else {
                for (int i = 0; i < nb; i++)
                    CounterApplication.count.increment(System.currentTimeMillis());

            }
            CounterApplication.count.saveMeNow();
            CounterApplication.count.computePeaks(System.currentTimeMillis());
            return "{\"status\":\"ok\"}";
        }
        return "{\"status\":\"error\"}";
    }
}
