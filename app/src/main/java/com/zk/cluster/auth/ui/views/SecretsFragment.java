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
import com.zk.cluster.auth.vault.SecretEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecretsFragment extends Fragment implements SecretEntryAdapter.Listener {
    private SecretEntryAdapter _adapter;
    private RecyclerView _recyclerView;
    private LinearLayout _emptyStateView;
    private Listener _listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_secrets, container, false);

        _adapter = new SecretEntryAdapter(this);

        _recyclerView = view.findViewById(R.id.rvSecretEntries);
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

    private List<SecretEntry> _allEntries = new ArrayList<>();
    private String _searchFilter = null;

    public void setEntries(Collection<SecretEntry> entries) {
        _allEntries = new ArrayList<>(entries);
        applyFilter();
    }

    public void clearEntries() {
        _allEntries = new ArrayList<>();
        applyFilter();
    }

    public void setSearchFilter(String search) {
        _searchFilter = (search != null && !search.isEmpty()) ? search.toLowerCase().trim() : null;
        applyFilter();
    }

    private void applyFilter() {
        if (_searchFilter == null) {
            _adapter.setEntries(_allEntries);
        } else {
            List<SecretEntry> filtered = new ArrayList<>();
            String[] tokens = _searchFilter.split("\\s+");
            for (SecretEntry entry : _allEntries) {
                boolean matchesAll = true;
                String title = entry.getTitle().toLowerCase();
                String tokenVal = entry.getToken().toLowerCase();
                String username = entry.getUsername().toLowerCase();
                String url = entry.getUrl().toLowerCase();
                String notes = entry.getNotes().toLowerCase();
                for (String token : tokens) {
                    boolean matchesToken = title.contains(token)
                            || tokenVal.contains(token)
                            || username.contains(token)
                            || url.contains(token)
                            || notes.contains(token);
                    if (!matchesToken) {
                        for (SecretEntry.CustomField cf : entry.getCustomFields()) {
                            if (cf.getLabel().toLowerCase().contains(token)
                                    || cf.getValue().toLowerCase().contains(token)) {
                                matchesToken = true;
                                break;
                            }
                        }
                    }
                    if (!matchesToken) {
                        matchesAll = false;
                        break;
                    }
                }
                if (matchesAll) {
                    filtered.add(entry);
                }
            }
            _adapter.setEntries(filtered);
        }
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
    public void onEntryClick(SecretEntry entry) {
        if (_listener != null) _listener.onSecretEntryClick(entry);
    }

    @Override
    public void onEntryLongClick(SecretEntry entry) {
        if (_listener != null) _listener.onSecretEntryLongClick(entry);
    }

    public interface Listener {
        void onSecretEntryClick(SecretEntry entry);
        void onSecretEntryLongClick(SecretEntry entry);
    }
}
