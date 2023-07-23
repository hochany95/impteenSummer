package com.impacteen.hochan.escaperoomapp.conf;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    public static final String PREF_NAME = "pref_data_name";
    public static final String TIME = "pref_time_value";
    public static final String GAME_STATE = "pref_game_state";
    public static final String NEED_HINT_INIT = "preg_need_hint_init";
    public static final int GAME_INIT = -1;
    public static final int GAME_STARTED = 1;
    public static final int GAME_FINISHED = 2;


    public static SharedPreferences getPref(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setTime (Context c, long value){
        SharedPreferences pref = getPref(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(TIME, value);
        editor.commit();
    }

    public static long getTime(Context c) {
        SharedPreferences pref = getPref(c);
        return pref.getLong(TIME, 0);
    }

    public static void resetTime(Context c) {
        SharedPreferences pref = getPref(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(TIME, 0);
        editor.commit();
    }

    public static void setGameStarted(Context c) {
        SharedPreferences pref = getPref(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(GAME_STATE, GAME_STARTED);
        editor.commit();
    }

    public static void setGameFinished(Context c) {
        SharedPreferences pref = getPref(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(GAME_STATE, GAME_STARTED);
        editor.commit();
    }

    public static void setGameReset(Context c) {
        SharedPreferences pref = getPref(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(GAME_STATE, GAME_INIT);
        editor.commit();
    }

    public static int getGameState(Context c) {
        SharedPreferences pref = getPref(c);
        return pref.getInt(GAME_STATE, GAME_INIT);
    }
    public static void clear(Context context) {
        SharedPreferences pref = getPref(context);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.commit();
    }

    public static void setNeedHintInit(Context c, boolean value) {
        SharedPreferences pref = getPref(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(NEED_HINT_INIT, value);
        editor.commit();
    }

    public static boolean getNeedHintInit(Context c) {
        SharedPreferences pref = getPref(c);
        return pref.getBoolean(NEED_HINT_INIT, false);
    }
}
