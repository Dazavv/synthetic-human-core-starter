package org.example.starter.audit.service.properties;

import lombok.Getter;
import lombok.Setter;
import org.example.starter.audit.service.AuditMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "audit")
public class AuditProperties {
    private AuditMode mode;
    private String kafkaTopic;
}