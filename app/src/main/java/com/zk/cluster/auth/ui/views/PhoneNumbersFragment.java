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
import java.util.List;

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

    private List<PhoneEntry> _allEntries = new ArrayList<>();
    private String _searchFilter = null;

    public void setEntries(Collection<PhoneEntry> entries) {
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
            List<PhoneEntry> filtered = new ArrayList<>();
            String[] tokens = _searchFilter.split("\\s+");
            for (PhoneEntry entry : _allEntries) {
                boolean matchesAll = true;
                String title = entry.getTitle().toLowerCase();
                String phoneNum = entry.getPhoneNumber().toLowerCase();
                String notes = entry.getNotes().toLowerCase();
                for (String token : tokens) {
                    boolean matchesToken = title.contains(token)
                            || phoneNum.contains(token)
                            || notes.contains(token);
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
