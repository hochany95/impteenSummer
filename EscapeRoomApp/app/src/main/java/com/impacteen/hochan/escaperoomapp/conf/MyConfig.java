package com.impacteen.hochan.escaperoomapp.conf;

public class MyConfig {

    public static boolean testMode = false;
    public static int CurrentStage = 0;
    public static int LAST_STAGE = 10;

    public static boolean isTestMode() {
        return testMode;
    }

    public static void setTestMode(boolean value) {
        testMode = value;
    }

    public static int getCurrentStage() {
        return CurrentStage;
    }

    public static final int CORRECT_ANSWER = 0;
    public static final int WRONG_ANSWER = 1;




}
