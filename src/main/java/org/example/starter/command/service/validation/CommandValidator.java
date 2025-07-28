package org.example.starter.command.service.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.example.starter.command.model.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommandValidator {
    private final Validator validator;

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
    public void validate(Command command) {
        Set<ConstraintViolation<Command>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Command> violation : violations) {
                sb.append(violation.getPropertyPath())
                        .append(": ")
                        .append(violation.getMessage())
                        .append("; ");
            }
            throw new jakarta.validation.ConstraintViolationException("Validation failed: " + sb, violations);
        }
    }
}
