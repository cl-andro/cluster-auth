package com.zk.cluster.auth.vault.slots;

import com.zk.cluster.auth.crypto.CryptParameters;

import java.util.UUID;

public class BiometricSlot extends RawSlot {
    public BiometricSlot() {
        super();
    }

    BiometricSlot(UUID uuid, byte[] key, CryptParameters keyParams) {
        super(uuid, key, keyParams);
    }

    @Override
    public byte getType() {
        return TYPE_BIOMETRIC;
    }
}
