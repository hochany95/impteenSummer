package com.impacteen.hochan.escaperoomapp.conf;

import android.util.Log;

public class MyConfig {
    public static final int CORRECT_ANSWER = 0;
    public static final int WRONG_ANSWER = 1;
    public static final int GO_NEXT = 2;
    public static final int PAUSE_MUSIC = 3;
    public static final int RESUME_MUSIC = 4;
    public static final int GAME_CLEAR = 5;
    public static final int LAST_STAGE =9;
    public static final long COOL_TIME = 600000;
    public static final long PENALTY_TIME = 600000;
    public static final String TEST_ANSWER = "1234";

    public static boolean TEST_MODE = false;
    public static boolean isTestMode() {
        return TEST_MODE;
    }

    public static void setTestMode(boolean value) {
        TEST_MODE = value;
    }
    public static int CurrentStage = 0;

    public static long START_TIME = 0L;
    public static long LAST_HINT_USED = 0L;


    public static boolean GAME_START = false;
    public static boolean GAME_FINISH = false;
}
