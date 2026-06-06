package com.zk.cluster.auth;

public enum CopyBehavior {
    NEVER,
    SINGLETAP,
    DOUBLETAP;

    private static CopyBehavior[] _values;

    static {
        _values = values();
    }

    public static CopyBehavior fromInteger(int x) {
        return _values[x];
    }
}
