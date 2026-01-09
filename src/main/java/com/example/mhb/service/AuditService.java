package com.example.mhb.service;

import com.example.mhb.entity.AuditLog;
import com.example.mhb.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditLogRepository auditRepository;

    public AuditService(AuditLogRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void log(
            String entity,
            Long entityId,
            String action,
            String oldValue,
            String newValue,
            String username
    ) {
        AuditLog log = new AuditLog();
        log.setEntityName(entity);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setPerformedBy(username);

        auditRepository.save(log);
    }
}
