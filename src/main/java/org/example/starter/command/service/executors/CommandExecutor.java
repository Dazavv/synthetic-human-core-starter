package org.example.starter.command.service.executors;

import lombok.extern.slf4j.Slf4j;
import org.example.starter.command.model.Command;

@Slf4j
public class CommandExecutor {
    public void execute(Command command) {
        log.info("Executing: {}", command.getDescription());
    }
}
