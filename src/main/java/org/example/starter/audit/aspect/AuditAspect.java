package org.example.starter.audit.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.starter.audit.model.AuditLog;
import org.example.starter.audit.model.AuditLogState;
import org.example.starter.audit.service.AuditLogger;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogger auditLogger;

    @Around("@annotation(org.example.starter.audit.annotation.WeylandWatchingYou)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        Object result = null;
        Exception error = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            error = e;
            throw e;
        } finally {
            AuditLog auditLog = AuditLog.builder()
                    .methodsName(methodName)
                    .arguments(args)
                    .result(result)
                    .timeStamp(Instant.now())
                    .auditLogState(error == null ? AuditLogState.SUCCESS : AuditLogState.FAILURE)
                    .build();

            auditLogger.log(auditLog);
        }
    }

}
