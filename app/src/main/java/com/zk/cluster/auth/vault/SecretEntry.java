package com.zk.cluster.auth.vault;

import com.zk.cluster.auth.util.UUIDMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecretEntry extends UUIDMap.Value {
    private String _title = "";
    private String _token = "";
    private String _username = "";
    private String _notes = "";
    private String _url = "";
    private boolean _isFavorite;
    private long _createdAt;
    private long _updatedAt;
    private List<CustomField> _customFields = new ArrayList<>();

    public SecretEntry() {
        super();
        _createdAt = System.currentTimeMillis();
        _updatedAt = _createdAt;
    }

    public SecretEntry(UUID uuid) {
        super(uuid);
        _createdAt = System.currentTimeMillis();
        _updatedAt = _createdAt;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("uuid", getUUID().toString());
            obj.put("title", _title);
            obj.put("token", _token);
            obj.put("username", _username);
            obj.put("notes", _notes);
            obj.put("url", _url);
            obj.put("favorite", _isFavorite);
            obj.put("createdAt", _createdAt);
            obj.put("updatedAt", _updatedAt);

            JSONArray fieldsArray = new JSONArray();
            for (CustomField field : _customFields) {
                JSONObject fieldObj = new JSONObject();
                fieldObj.put("label", field.getLabel());
                fieldObj.put("value", field.getValue());
                fieldsArray.put(fieldObj);
            }
            obj.put("customFields", fieldsArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static SecretEntry fromJson(JSONObject obj) throws JSONException {
        UUID uuid;
        if (!obj.has("uuid")) {
            uuid = UUID.randomUUID();
        } else {
            uuid = UUID.fromString(obj.getString("uuid"));
        }
        SecretEntry entry = new SecretEntry(uuid);
        entry.setTitle(obj.optString("title", ""));
        entry.setToken(obj.optString("token", ""));
        entry.setUsername(obj.optString("username", ""));
        entry.setNotes(obj.optString("notes", ""));
        entry.setUrl(obj.optString("url", ""));
        entry.setIsFavorite(obj.optBoolean("favorite", false));
        entry.setCreatedAt(obj.optLong("createdAt", System.currentTimeMillis()));
        entry.setUpdatedAt(obj.optLong("updatedAt", System.currentTimeMillis()));

        if (obj.has("customFields")) {
            JSONArray fieldsArray = obj.getJSONArray("customFields");
            for (int i = 0; i < fieldsArray.length(); i++) {
                JSONObject fieldObj = fieldsArray.getJSONObject(i);
                CustomField field = new CustomField(
                        fieldObj.optString("label", ""),
                        fieldObj.optString("value", "")
                );
                entry.getCustomFields().add(field);
            }
        }

        return entry;
    }

    public String getTitle() { return _title; }
    public void setTitle(String title) { _title = title; _updatedAt = System.currentTimeMillis(); }

    public String getToken() { return _token; }
    public void setToken(String token) { _token = token; _updatedAt = System.currentTimeMillis(); }

    public String getUsername() { return _username; }
    public void setUsername(String username) { _username = username; _updatedAt = System.currentTimeMillis(); }

    public String getNotes() { return _notes; }
    public void setNotes(String notes) { _notes = notes; _updatedAt = System.currentTimeMillis(); }

    public String getUrl() { return _url; }
    public void setUrl(String url) { _url = url; _updatedAt = System.currentTimeMillis(); }

    public boolean isFavorite() { return _isFavorite; }
    public void setIsFavorite(boolean favorite) { _isFavorite = favorite; _updatedAt = System.currentTimeMillis(); }

    public long getCreatedAt() { return _createdAt; }
    public void setCreatedAt(long createdAt) { _createdAt = createdAt; }

    public long getUpdatedAt() { return _updatedAt; }
    public void setUpdatedAt(long updatedAt) { _updatedAt = updatedAt; }

    public List<CustomField> getCustomFields() { return _customFields; }
    public void setCustomFields(List<CustomField> customFields) { _customFields = customFields; _updatedAt = System.currentTimeMillis(); }

    public static class CustomField {
        private String _label;
        private String _value;

        public CustomField(String label, String value) {
            _label = label;
            _value = value;
        }

        public String getLabel() { return _label; }
        public void setLabel(String label) { _label = label; }

        public String getValue() { return _value; }
        public void setValue(String value) { _value = value; }
    }
}
