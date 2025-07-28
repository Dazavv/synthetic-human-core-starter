package org.example.starter.command.service.executors;

import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.starter.command.model.Command;
import org.example.starter.command.model.CommandPriority;
import org.example.starter.command.service.exceptions.QueueOverflowException;
import org.example.starter.command.service.validation.CommandValidator;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Slf4j
@Service
public class CommandQueueService {

    @Getter
    private final BlockingQueue<Runnable> commandsQueue;
    private final ThreadPoolExecutor executor;
    private final CommandValidator validator;
    private final CommandExecutor commandExecutor;
    private final int CORE_POOL_SIZE = 2;
    private final int MAX_POOL_SIZE = 4;
    private final int QUEUE_CAPACITY = 100;


    public CommandQueueService(CommandValidator validator) {
        this.commandsQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
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

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}
