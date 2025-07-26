package org.example.starter.audit.service;

import org.example.starter.audit.model.AuditLog;

public interface AuditLogger {
    void log(AuditLog auditLog);
}
