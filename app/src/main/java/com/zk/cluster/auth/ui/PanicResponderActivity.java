package com.zk.cluster.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.zk.cluster.auth.BuildConfig;
import com.zk.cluster.auth.R;
import com.zk.cluster.auth.crypto.pins.GuardianProjectFDroidRSA2048;
import com.zk.cluster.auth.vault.VaultRepository;

import info.guardianproject.GuardianProjectRSA4096;
import info.guardianproject.trustedintents.TrustedIntents;

public class PanicResponderActivity extends ClusterActivity {
    public static final String PANIC_TRIGGER_ACTION = "info.guardianproject.panic.action.TRIGGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!_prefs.isPanicTriggerEnabled()) {
            Toast.makeText(this, R.string.panic_trigger_ignore_toast, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Intent intent;
        if (!BuildConfig.TEST.get()) {
            TrustedIntents trustedIntents = TrustedIntents.get(this);
            trustedIntents.addTrustedSigner(GuardianProjectRSA4096.class);
            trustedIntents.addTrustedSigner(GuardianProjectFDroidRSA2048.class);

            intent = trustedIntents.getIntentFromTrustedSender(this);
        } else {
            intent = getIntent();
        }

        if (intent != null && PANIC_TRIGGER_ACTION.equals(intent.getAction())) {
            VaultRepository.deleteFile(this);
            _vaultManager.lock(false);
            finishApp();
            return;
        }

        finish();
    }

    private void finishApp() {
        ExitActivity.exitAppAndRemoveFromRecents(this);
        finishAndRemoveTask();
    }
}
