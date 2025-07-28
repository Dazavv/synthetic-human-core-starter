package org.example.starter.audit.service.console;

import lombok.extern.slf4j.Slf4j;
import org.example.starter.audit.model.AuditLog;
import org.example.starter.audit.service.AuditLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(name = "audit.mode", havingValue = "CONSOLE", matchIfMissing = true)
public class ConsoleAudit implements AuditLogger {

    @Override
    public void log(AuditLog auditLog) {
        log.info("AUDIT_LOG: {}", auditLog);
    }
}
