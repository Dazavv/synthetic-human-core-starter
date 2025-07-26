package org.example.starter.audit.service.kafka;

import lombok.RequiredArgsConstructor;
import org.example.starter.audit.model.AuditLog;
import org.example.starter.audit.service.AuditLogger;
import org.example.starter.audit.service.properties.AuditProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "audit.mode", havingValue = "KAFKA")
public class KafkaAudit implements AuditLogger {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AuditProperties auditProperties;

    @Override
    public void log(AuditLog auditLog) {
        kafkaTemplate.send(auditProperties.getKafkaTopic(), auditLog);

    }
}
