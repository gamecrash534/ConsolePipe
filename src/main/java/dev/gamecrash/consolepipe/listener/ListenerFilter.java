package dev.gamecrash.consolepipe.listener;

import lombok.Setter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public final class ListenerFilter extends AbstractFilter {
    @Setter @Nullable
    private static Consumer<LogEvent> eventConsumer;

    @Override
    public Result filter(LogEvent e) {
        if (eventConsumer != null) eventConsumer.accept(e.toImmutable());
        return Result.NEUTRAL;
    }
}
