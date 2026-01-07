package com.itsoeh.checkmedocente.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.itsoeh.checkmedocente.modelo.MDocente;

public class SessionManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_DOCENTE = "doc_data";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        gson = new Gson();
    }


    public void saveSession(MDocente docente) {
        String json = gson.toJson(docente);
        editor.putString(KEY_DOCENTE, json);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public MDocente getDoc() {
        String json = prefs.getString(KEY_DOCENTE, null);
        if (json != null) {
            return gson.fromJson(json, MDocente.class);
        }
        return null;
    }
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
