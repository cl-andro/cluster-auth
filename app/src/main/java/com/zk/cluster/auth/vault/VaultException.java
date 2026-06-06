package com.zk.cluster.auth.vault;

public class VaultException extends Exception {
    public VaultException(Throwable cause) {
        super(cause);
    }

    public VaultException(String message) {
        super(message);
    }
}
