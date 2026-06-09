package com.zk.cluster.auth.ui.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zk.cluster.auth.R;
import com.zk.cluster.auth.vault.PasswordEntry;

import java.util.ArrayList;
import java.util.List;

public class PasswordEntryAdapter extends RecyclerView.Adapter<PasswordEntryAdapter.ViewHolder> {
    private List<PasswordEntry> _entries = new ArrayList<>();
    private Listener _listener;

    public PasswordEntryAdapter(Listener listener) {
        _listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_password_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PasswordEntry entry = _entries.get(position);
        Context ctx = holder.itemView.getContext();

        holder.textTitle.setText(entry.getTitle());
        holder.textUsername.setText(entry.getUsername());

        if (entry.isFavorite()) {
            holder.iconFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.iconFavorite.setVisibility(View.GONE);
        }

        holder.layoutCustomFieldsPreview.removeAllViews();
        if (!entry.getCustomFields().isEmpty()) {
            int maxPreview = Math.min(entry.getCustomFields().size(), 2);
            for (int i = 0; i < maxPreview; i++) {
                PasswordEntry.CustomField field = entry.getCustomFields().get(i);
                TextView tv = new TextView(ctx);
                tv.setText(String.format("%s: %s", field.getLabel(), field.getValue()));
                tv.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodySmall);
                tv.setMaxLines(1);
                tv.setEllipsize(android.text.TextUtils.TruncateAt.END);
                holder.layoutCustomFieldsPreview.addView(tv);
            }
            if (entry.getCustomFields().size() > 2) {
                TextView tv = new TextView(ctx);
                tv.setText(ctx.getString(R.string.pw_vault_more_fields, entry.getCustomFields().size() - 2));
                tv.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodySmall);
                tv.setTextColor(ctx.getColor(com.google.android.material.R.attr.colorPrimary));
                holder.layoutCustomFieldsPreview.addView(tv);
            }
            holder.layoutCustomFieldsPreview.setVisibility(View.VISIBLE);
        } else {
            holder.layoutCustomFieldsPreview.setVisibility(View.GONE);
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

    public void setEntries(List<PasswordEntry> entries) {
        _entries = new ArrayList<>(entries);
        notifyDataSetChanged();
    }

    public PasswordEntry getEntry(int position) {
        return _entries.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textUsername;
        ImageView iconFavorite;
        LinearLayout layoutCustomFieldsPreview;

        ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textUsername = itemView.findViewById(R.id.text_username);
            iconFavorite = itemView.findViewById(R.id.icon_favorite);
            layoutCustomFieldsPreview = itemView.findViewById(R.id.layout_custom_fields_preview);
        }
    }

    public interface Listener {
        void onEntryClick(PasswordEntry entry);
        void onEntryLongClick(PasswordEntry entry);
    }
}
