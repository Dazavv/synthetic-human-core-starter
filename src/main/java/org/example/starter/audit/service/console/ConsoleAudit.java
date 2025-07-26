package org.example.starter.audit.service.console;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.starter.audit.model.AuditLog;
import org.example.starter.audit.service.AuditLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "audit.mode", havingValue = "CONSOLE")
public class ConsoleAudit implements AuditLogger {

    @Override
    public void log(AuditLog auditLog) {
        log.info("AUDIT_LOG: {}", new Gson().toJson(auditLog));
    }
}
