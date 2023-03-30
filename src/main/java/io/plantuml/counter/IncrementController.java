package io.plantuml.counter;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncrementController {

    @PostMapping(value = "/v2-increment", produces = "application/json")
    public String increment(@RequestBody String data) {
        JSONObject obj = new JSONObject(data);
        if (obj.has("nb")) {
            final int nb = obj.getInt("nb");
            for (int i = 0; i < nb; i++)
                CounterApplication.count.increment(System.currentTimeMillis());

            CounterApplication.count.saveMeNow();

            return "{\"status\":\"ok\"}";
        }
        return "{\"status\":\"error\"}";
    }
}
