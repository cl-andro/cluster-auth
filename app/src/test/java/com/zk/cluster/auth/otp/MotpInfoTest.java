package com.zk.cluster.auth.otp;

import static org.junit.Assert.assertEquals;

import com.zk.cluster.auth.crypto.otp.MOTPTest;
import com.zk.cluster.auth.encoding.EncodingException;
import com.zk.cluster.auth.encoding.Hex;

import org.junit.Test;

public class MotpInfoTest {
    @Test
    public void testMotpInfoOtp() throws OtpInfoException, EncodingException {
        for (MOTPTest.Vector vector : MOTPTest.VECTORS) {
            MotpInfo info = new MotpInfo(Hex.decode(vector.Secret), vector.Pin);
            assertEquals(vector.OTP, info.getOtp(vector.Time));
        }
    }
}
