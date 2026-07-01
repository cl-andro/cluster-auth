package com.zk.cluster.auth.vault;

import com.zk.cluster.auth.util.UUIDMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class PhoneEntry extends UUIDMap.Value {
    private String _title = "";
    private String _phoneNumber = "";
    private String _notes = "";
    private boolean _isFavorite;
    private long _createdAt;
    private long _updatedAt;

    public PhoneEntry() {
        super();
        _createdAt = System.currentTimeMillis();
        _updatedAt = _createdAt;
    }

    public PhoneEntry(UUID uuid) {
        super(uuid);
        _createdAt = System.currentTimeMillis();
        _updatedAt = _createdAt;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("uuid", getUUID().toString());
            obj.put("title", _title);
            obj.put("phoneNumber", _phoneNumber);
            obj.put("notes", _notes);
            obj.put("favorite", _isFavorite);
            obj.put("createdAt", _createdAt);
            obj.put("updatedAt", _updatedAt);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static PhoneEntry fromJson(JSONObject obj) throws JSONException {
        UUID uuid;
        if (!obj.has("uuid")) {
            uuid = UUID.randomUUID();
        } else {
            uuid = UUID.fromString(obj.getString("uuid"));
        }
        PhoneEntry entry = new PhoneEntry(uuid);
        entry.setTitle(obj.optString("title", ""));
        entry.setPhoneNumber(obj.optString("phoneNumber", ""));
        entry.setNotes(obj.optString("notes", ""));
        entry.setIsFavorite(obj.optBoolean("favorite", false));
        entry.setCreatedAt(obj.optLong("createdAt", System.currentTimeMillis()));
        entry.setUpdatedAt(obj.optLong("updatedAt", System.currentTimeMillis()));

        return entry;
    }

    public String getTitle() { return _title; }
    public void setTitle(String title) { _title = title; _updatedAt = System.currentTimeMillis(); }

    public String getPhoneNumber() { return _phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { _phoneNumber = phoneNumber; _updatedAt = System.currentTimeMillis(); }

    public String getNotes() { return _notes; }
    public void setNotes(String notes) { _notes = notes; _updatedAt = System.currentTimeMillis(); }

    public boolean isFavorite() { return _isFavorite; }
    public void setIsFavorite(boolean favorite) { _isFavorite = favorite; _updatedAt = System.currentTimeMillis(); }

    public long getCreatedAt() { return _createdAt; }
    public void setCreatedAt(long createdAt) { _createdAt = createdAt; }

    public long getUpdatedAt() { return _updatedAt; }
    public void setUpdatedAt(long updatedAt) { _updatedAt = updatedAt; }
}
