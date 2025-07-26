package org.example.starter.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Command {
    @Size(max = 1000)
    @NotBlank
    private String description;
    private CommandPriority priority;
    @NotBlank
    @Size(max = 100)
    private String author;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Time must be in format yyyy-MM-dd")
    private String time; //в формате 2025-07-25


}
