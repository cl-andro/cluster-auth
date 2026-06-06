package com.zk.cluster.auth.ui.models;

import com.zk.cluster.auth.database.AuditLogEntry;
import com.zk.cluster.auth.vault.VaultEntry;

import javax.annotation.Nullable;

public class AuditLogEntryModel {
    private AuditLogEntry _auditLogEntry;
    private VaultEntry _referencedVaultEntry;

    public AuditLogEntryModel(AuditLogEntry auditLogEntry, @Nullable VaultEntry referencedVaultEntry) {
        _auditLogEntry = auditLogEntry;
        _referencedVaultEntry = referencedVaultEntry;
    }

    public AuditLogEntry getAuditLogEntry() {
        return _auditLogEntry;
    }

    public VaultEntry getReferencedVaultEntry() {
        return _referencedVaultEntry;
    }
}
