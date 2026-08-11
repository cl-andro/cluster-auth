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
import com.zk.cluster.auth.vault.NoteEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesFragment extends Fragment implements NoteEntryAdapter.Listener {
    private NoteEntryAdapter _adapter;
    private RecyclerView _recyclerView;
    private LinearLayout _emptyStateView;
    private Listener _listener;
    private List<NoteEntry> _allEntries = new ArrayList<>();
    private String _searchFilter = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        _adapter = new NoteEntryAdapter(this);

        _recyclerView = view.findViewById(R.id.rvNoteEntries);
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

    public void setEntries(Collection<NoteEntry> entries) {
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
            List<NoteEntry> filtered = new ArrayList<>();
            String[] tokens = _searchFilter.split("\\s+");
            for (NoteEntry entry : _allEntries) {
                boolean matchesAll = true;
                String title = entry.getTitle().toLowerCase();
                String notes = entry.getNotes().toLowerCase();
                for (String token : tokens) {
                    boolean matchesToken = title.contains(token)
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
    public void onEntryClick(NoteEntry entry) {
        if (_listener != null) _listener.onNoteEntryClick(entry);
    }

    @Override
    public void onEntryLongClick(NoteEntry entry) {
        if (_listener != null) _listener.onNoteEntryLongClick(entry);
    }

    public interface Listener {
        void onNoteEntryClick(NoteEntry entry);
        void onNoteEntryLongClick(NoteEntry entry);
    }
}
