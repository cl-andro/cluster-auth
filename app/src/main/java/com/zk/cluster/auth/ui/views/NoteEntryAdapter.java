package com.zk.cluster.auth.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zk.cluster.auth.R;
import com.zk.cluster.auth.vault.NoteEntry;

import java.util.ArrayList;
import java.util.List;

public class NoteEntryAdapter extends RecyclerView.Adapter<NoteEntryAdapter.ViewHolder> {
    private List<NoteEntry> _entries = new ArrayList<>();
    private Listener _listener;

    public NoteEntryAdapter(Listener listener) {
        _listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteEntry entry = _entries.get(position);

        holder.textTitle.setText(entry.getTitle());

        if (entry.isFavorite()) {
            holder.iconFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.iconFavorite.setVisibility(View.GONE);
        }

        String notes = entry.getNotes();
        if (notes != null && !notes.isEmpty()) {
            holder.textNotes.setVisibility(View.VISIBLE);
            holder.textNotes.setText(notes);
        } else {
            holder.textNotes.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (_listener != null) _listener.onEntryClick(entry);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (_listener != null) _listener.onEntryLongClick(entry);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return _entries.size();
    }

    public void setEntries(List<NoteEntry> entries) {
        _entries = new ArrayList<>(entries);
        notifyDataSetChanged();
    }

    public NoteEntry getEntry(int position) {
        return _entries.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textNotes;
        ImageView iconFavorite;

        ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textNotes = itemView.findViewById(R.id.text_notes);
            iconFavorite = itemView.findViewById(R.id.icon_favorite);
        }
    }

    public interface Listener {
        void onEntryClick(NoteEntry entry);
        void onEntryLongClick(NoteEntry entry);
    }
}
