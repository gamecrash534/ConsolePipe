package xyz.gamecrash.consolepipe.utils;

import xyz.gamecrash.consolepipe.ConsolePipe;

public class MessageBuilder {
    private final ConsolePipe plugin = ConsolePipe.getPlugin();
    private String message;

    public MessageBuilder(String message) {
        this.message = message;
    }
    public static MessageBuilder fromConfig(String configKey) {
        return new MessageBuilder(MessageUtils.returnConfig(configKey));
    }

    public MessageBuilder append(String text) {
        message += text;
        return this;
    }
    public MessageBuilder appendLine(String text) {
        message += text + "\n";
        return this;
    }
    public MessageBuilder prefix() {
        message = plugin.getConfig().getString("prefix") + message;
        return this;
    }
    public MessageBuilder replace(String placeholder, String value) {
        message = message.replace("%" + placeholder + "%", value);
        return this;
    }
    public String build() {
        return message.toString();
    }

    @Override
    public String toString() {
        return build();
    }
}
