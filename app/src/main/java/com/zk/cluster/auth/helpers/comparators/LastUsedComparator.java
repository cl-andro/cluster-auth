package com.zk.cluster.auth.helpers.comparators;

import com.zk.cluster.auth.vault.VaultEntry;

import java.util.Comparator;

public class LastUsedComparator implements Comparator<VaultEntry> {
    @Override
    public int compare(VaultEntry a, VaultEntry b) {
        return Long.compare(a.getLastUsedTimestamp(), b.getLastUsedTimestamp());
    }
}
