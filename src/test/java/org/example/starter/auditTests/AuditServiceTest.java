package org.example.starter.auditTests;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.example.starter.audit.aspect.AuditAspect;
import org.example.starter.audit.model.AuditLogState;
import org.example.starter.audit.service.AuditLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuditServiceTest {
    @Mock
    private AuditLogger auditLogger;

    @InjectMocks
    private AuditAspect auditAspect;

    @Test
    @DisplayName("Should log success when method executes without exception")
    void testAuditSuccess() throws Throwable {
        ProceedingJoinPoint pjp = Mockito.mock(ProceedingJoinPoint.class);
        Signature signature = Mockito.mock(Signature.class);

        Mockito.when(pjp.getArgs()).thenReturn(new Object[]{"arg1", 123});
        Mockito.when(signature.getName()).thenReturn("testedMethod");
        Mockito.when(pjp.getSignature()).thenReturn(signature);
        Mockito.when(pjp.proceed()).thenReturn("result");

        Object result = auditAspect.audit(pjp);

        assertEquals("result", result);

        Mockito.verify(auditLogger).log(Mockito.argThat(auditLog ->
                auditLog.getMethodsName().equals("testedMethod") &&
                        auditLog.getResult().equals("result") &&
                        auditLog.getAuditLogState() == AuditLogState.SUCCESS
        ));
    }

    @Test
    @DisplayName("Should log failure when method executes with exception")
    void testAuditFailure() throws Throwable {
        ProceedingJoinPoint pjp = Mockito.mock(ProceedingJoinPoint.class);
        Signature signature = Mockito.mock(Signature.class);

        Mockito.when(pjp.getArgs()).thenReturn(new Object[]{});
        Mockito.when(pjp.getSignature()).thenReturn(signature);
        Mockito.when(pjp.proceed()).thenThrow(new RuntimeException("fail"));

        assertThrows(RuntimeException.class, () -> auditAspect.audit(pjp));

        Mockito.verify(auditLogger).log(Mockito.argThat(auditLog ->
                        auditLog.getAuditLogState() == AuditLogState.FAILURE
        ));
    }

}
