package org.example.starter.audit.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AuditLog {

    private String methodsName;
    private Object[] arguments;
    private Object result;
    private Instant timeStamp;
    private AuditLogState auditLogState;
}
