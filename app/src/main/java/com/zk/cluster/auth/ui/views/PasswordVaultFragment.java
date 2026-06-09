package com.zk.cluster.auth.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zk.cluster.auth.R;
import com.zk.cluster.auth.vault.PasswordEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PasswordVaultFragment extends Fragment implements PasswordEntryAdapter.Listener {
    private PasswordEntryAdapter _adapter;
    private RecyclerView _recyclerView;
    private LinearLayout _emptyStateView;
    private Listener _listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_vault, container, false);

        _adapter = new PasswordEntryAdapter(this);

        _recyclerView = view.findViewById(R.id.rvPasswordEntries);
        _recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        _recyclerView.setAdapter(_adapter);

        _emptyStateView = view.findViewById(R.id.vEmptyList);

        return view;
    }

    public void setEntries(Collection<PasswordEntry> entries) {
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
    public void onEntryClick(PasswordEntry entry) {
        if (_listener != null) _listener.onPasswordEntryClick(entry);
    }

    @Override
    public void onEntryLongClick(PasswordEntry entry) {
        if (_listener != null) _listener.onPasswordEntryLongClick(entry);
    }

    public interface Listener {
        void onPasswordEntryClick(PasswordEntry entry);
        void onPasswordEntryLongClick(PasswordEntry entry);
    }
}
