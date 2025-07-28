package org.example.starter.command.service.executors;

import lombok.extern.slf4j.Slf4j;
import org.example.starter.command.model.Command;
import org.example.starter.command.model.CommandPriority;
import org.example.starter.command.service.exceptions.QueueOverflowException;
import org.example.starter.command.service.validation.CommandValidator;

import java.util.concurrent.*;

@Slf4j
public class CommandQueueService {

    private final BlockingQueue<Runnable> commandsQueue;
    private final ThreadPoolExecutor executor;
    private final CommandValidator validator;
    private final CommandExecutor commandExecutor;

    public CommandQueueService(int corePoolSize,
                               int maxPoolSize,
                               int queueCapacity,
                               CommandValidator validator) {
        this.commandsQueue = new ArrayBlockingQueue<>(queueCapacity);
        this.executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.SECONDS,
                commandsQueue,
                new ThreadPoolExecutor.AbortPolicy()
        );
        this.commandExecutor = new CommandExecutor();
        this.validator = validator;
    }

    public void processCommand(Command command) {
        validator.validate(command);

        if (command.getPriority() == CommandPriority.CRITICAL) {
            commandExecutor.execute(command);
        } else {
            try {
                log.debug("Submitting command to thread pool: {}", command);
                executor.execute(() -> commandExecutor.execute(command));
            } catch (RejectedExecutionException e) {
                throw new QueueOverflowException("Command queue is full", e);
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
