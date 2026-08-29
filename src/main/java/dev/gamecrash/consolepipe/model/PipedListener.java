package dev.gamecrash.consolepipe.model;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.regex.Matcher;

public record PipedListener(Audience audience, Matcher matcher) {
    public boolean matches(String input) {
        return matcher.reset(input).matches();
    }

    public void sendMessage(Component message) {
        audience.sendMessage(message);
    }
}
