package com.zk.cluster.auth.otp;

import android.net.Uri;

public interface Transferable {
    Uri getUri() throws GoogleAuthInfoException;
}
