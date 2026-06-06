package com.zk.cluster.auth.vectors;

import com.zk.cluster.auth.encoding.Base32;
import com.zk.cluster.auth.encoding.EncodingException;
import com.zk.cluster.auth.otp.HotpInfo;
import com.zk.cluster.auth.otp.OtpInfoException;
import com.zk.cluster.auth.otp.SteamInfo;
import com.zk.cluster.auth.otp.TotpInfo;
import com.zk.cluster.auth.vault.VaultEntry;
import com.google.common.collect.Lists;

import java.util.List;

public class VaultEntries {
    private VaultEntries() {

    }

    public static List<VaultEntry> get() {
        try {
            return Lists.newArrayList(
                    new VaultEntry(new TotpInfo(Base32.decode("4SJHB4GSD43FZBAI7C2HLRJGPQ")), "Mason", "Deno"),
                    new VaultEntry(new TotpInfo(Base32.decode("5OM4WOOGPLQEF6UGN3CPEOOLWU"), "SHA256", 7, 20), "James", "SPDX"),
                    new VaultEntry(new TotpInfo(Base32.decode("7ELGJSGXNCCTV3O6LKJWYFV2RA"), "SHA512", 8, 50), "Elijah", "Airbnb"),
                    new VaultEntry(new HotpInfo(Base32.decode("YOOMIXWS5GN6RTBPUFFWKTW5M4"), "SHA1", 6, 1), "James", "Issuu"),
                    new VaultEntry(new HotpInfo(Base32.decode("KUVJJOM753IHTNDSZVCNKL7GII"), "SHA256", 7, 50), "Benjamin", "Air Canada"),
                    new VaultEntry(new HotpInfo(Base32.decode("5VAML3X35THCEBVRLV24CGBKOY"), "SHA512", 8, 10300), "Mason", "WWE"),
                    new VaultEntry(new SteamInfo(Base32.decode("JRZCL47CMXVOQMNPZR2F7J4RGI"), "SHA1", 5, 30), "Sophia", "Boeing"),
                    new VaultEntry(new TotpInfo(Base32.decode("BMGRXPGFARQQF4GMT25JATL2VYLAHDBI"), "SHA1", 8, 30), "US-2211-2050-3346", "Battle.net")
            );
        } catch (OtpInfoException | EncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
