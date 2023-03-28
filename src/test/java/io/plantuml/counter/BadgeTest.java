package io.plantuml.counter;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;

public class BadgeTest {

    @Test
    public void basic() {
        final Badge badge = new Badge("my label", "my message", Badge.COLOR_IMPORTANT);

        final String json = badge.toJSonString();

        MatcherAssert.assertThat(json, containsString("important"));
        MatcherAssert.assertThat(json, containsString("my label"));
        MatcherAssert.assertThat(json, containsString("my message"));

    }


}