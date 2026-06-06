package com.zk.cluster.auth.vault;

public class VaultRepositoryException extends Exception {
    public VaultRepositoryException(Throwable cause) {
        super(cause);
    }

    public VaultRepositoryException(String message) {
        super(message);
    }
}
