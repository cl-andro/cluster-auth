package com.zk.cluster.auth.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zk.cluster.auth.R;
import com.zk.cluster.auth.vault.PhoneEntry;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneNumbersFragment extends Fragment implements PhoneEntryAdapter.Listener {
    private PhoneEntryAdapter _adapter;
    private RecyclerView _recyclerView;
    private LinearLayout _emptyStateView;
    private Listener _listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_entries, container, false);

        _adapter = new PhoneEntryAdapter(this);

        _recyclerView = view.findViewById(R.id.rvPhoneEntries);
        _recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        _recyclerView.setAdapter(_adapter);

        final int rvInitialPaddingLeft = _recyclerView.getPaddingLeft();
        final int rvInitialPaddingTop = _recyclerView.getPaddingTop();
        final int rvInitialPaddingRight = _recyclerView.getPaddingRight();
        final int rvInitialPaddingBottom = _recyclerView.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(_recyclerView, (targetView, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            targetView.setPadding(
                    rvInitialPaddingLeft,
                    rvInitialPaddingTop,
                    rvInitialPaddingRight,
                    rvInitialPaddingBottom + insets.bottom
            );
            return windowInsets;
        });

        _emptyStateView = view.findViewById(R.id.vEmptyList);

        return view;
    }

    public void setEntries(Collection<PhoneEntry> entries) {
        _adapter.setEntries(new ArrayList<>(entries));
        updateEmptyState();
    }

    public void clearEntries() {
        _adapter.setEntries(new ArrayList<>());
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (_adapter.getItemCount() > 0) {
            _recyclerView.setVisibility(View.VISIBLE);
            _emptyStateView.setVisibility(View.GONE);
        } else {
            _recyclerView.setVisibility(View.GONE);
            _emptyStateView.setVisibility(View.VISIBLE);
        }
    }

    public void setListener(Listener listener) {
        _listener = listener;
    }

    @Override
    public void onEntryClick(PhoneEntry entry) {
        if (_listener != null) _listener.onPhoneEntryClick(entry);
    }

    @Override
    public void onEntryLongClick(PhoneEntry entry) {
        if (_listener != null) _listener.onPhoneEntryLongClick(entry);
    }

    public interface Listener {
        void onPhoneEntryClick(PhoneEntry entry);
        void onPhoneEntryLongClick(PhoneEntry entry);
    }
}
