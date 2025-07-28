package org.example.starter.command.service.validation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.starter.command.model.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandValidator {
    private final Validator validator;

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
    public void validate(Command command) {
        var violations = validator.validate(command);
        log.info(command.toString());

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
