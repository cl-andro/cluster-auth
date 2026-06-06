package com.zk.cluster.auth.helpers.comparators;

import com.zk.cluster.auth.vault.VaultEntry;

import java.util.Comparator;

public class AccountNameComparator implements Comparator<VaultEntry> {
    @Override
    public int compare(VaultEntry a, VaultEntry b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
}