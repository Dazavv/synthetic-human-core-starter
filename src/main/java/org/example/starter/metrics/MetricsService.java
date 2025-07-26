package org.example.starter.metrics;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class MetricsService {
    private static final String BUSYNESS_METRIC = "android_busyness";
    private static final String COMPLETED_TASKS_METRIC = "android_completed_tasks";
    private static final String AUTHOR_LABEL = "author";

    private final MeterRegistry meterRegistry;
    private final AtomicLong busynessGaugeValue = new AtomicLong();
    private final Map<String, Counter> authorCounters = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        Gauge.builder(BUSYNESS_METRIC, busynessGaugeValue::get)
                .description("Current size of android task queue")
                .register(meterRegistry);
    }

    public void updateQueueSize(int size) {
        busynessGaugeValue.set(size);
    }

    public void taskCompletedBy(String author) {
        authorCounters
                .computeIfAbsent(author, a ->
                        Counter.builder(COMPLETED_TASKS_METRIC)
                                .tag(AUTHOR_LABEL, a)
                                .description("Completed tasks by author")
                                .register(meterRegistry))
                .increment();
    }
}
