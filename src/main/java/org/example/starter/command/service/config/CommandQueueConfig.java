package org.example.starter.command.service.config;

import org.example.starter.command.service.executors.CommandQueueService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class CommandQueueConfig {

    @Bean
    public CommandQueueService commandQueueService(
            @Value("${thread-pool.core-size}") int coreSize,
            @Value("${thread-pool.max-size}") int maxSize,
            @Value("${thread-pool.queue-capacity}") int queueCapacity
    ) {
        return new CommandQueueService(coreSize, maxSize, queueCapacity);
    }
}
