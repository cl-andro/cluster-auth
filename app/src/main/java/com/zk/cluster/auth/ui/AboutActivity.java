package com.zk.cluster.auth.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AttrRes;
import androidx.annotation.StringRes;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zk.cluster.auth.BuildConfig;
import com.zk.cluster.auth.R;
import com.zk.cluster.auth.helpers.ViewHelper;
import com.google.android.material.color.MaterialColors;

public class AboutActivity extends ClusterActivity {

    private static String GITHUB = "https://github.com/cl-andro/cluster-vault-release.git";

    private static String MAIL_BEEMDEVELOPMENT = "zkalamgir@proton.me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (abortIfOrphan(savedInstanceState)) {
            return;
        }

        setContentView(R.layout.activity_about);
        setSupportActionBar(findViewById(R.id.toolbar));
        ViewHelper.setupAppBarInsets(findViewById(R.id.app_bar_layout));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView appVersion = findViewById(R.id.app_version);
        appVersion.setText(getCurrentAppVersion());

        View btnAppVersion = findViewById(R.id.btn_app_version);
        btnAppVersion.setOnClickListener(v -> {
            copyToClipboard(getCurrentAppVersion(), R.string.version_copied);
        });

        View btnGithub = findViewById(R.id.btn_github);
        btnGithub.setOnClickListener(v -> openUrl(GITHUB));

        View btnMail = findViewById(R.id.btn_email);
        btnMail.setOnClickListener(v -> openMail(MAIL_BEEMDEVELOPMENT));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.about_scroll_view), (targetView, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            targetView.setPadding(
                    0,
                    0,
                    0,
                    insets.bottom
            );
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private static String getCurrentAppVersion() {
        if (BuildConfig.DEBUG) {
            return String.format("%s-%s (%s)", BuildConfig.VERSION_NAME, BuildConfig.GIT_HASH, BuildConfig.GIT_BRANCH);
        }

        return BuildConfig.VERSION_NAME;
    }

    private void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(browserIntent);
    }

    private void copyToClipboard(String text, @StringRes int messageId) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("text/plain", text);
        clipboard.setPrimaryClip(data);
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    private void openMail(String mailaddress) {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        mailIntent.setData(Uri.parse("mailto:" + mailaddress));
        mailIntent.putExtra(Intent.EXTRA_EMAIL, mailaddress);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name_full);

        startActivity(Intent.createChooser(mailIntent, getString(R.string.email)));
    }

    private String getThemeColorAsHex(@AttrRes int attributeId) {
        int color = MaterialColors.getColor(this, attributeId, getClass().getCanonicalName());
        return String.format("%06X", 0xFFFFFF & color);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
