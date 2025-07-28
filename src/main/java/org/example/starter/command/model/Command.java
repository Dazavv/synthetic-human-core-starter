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
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?(Z|[+-]\\d{2}:\\d{2})?)?$")
    private String time;




}
