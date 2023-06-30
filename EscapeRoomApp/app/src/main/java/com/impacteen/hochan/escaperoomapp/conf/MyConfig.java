package com.impacteen.hochan.escaperoomapp.conf;

public class MyConfig {

    public static boolean TEST_MODE = false;
    public static int CurrentStage = 0;
    public static int LAST_STAGE = 10;
    public static int LAST_OPEN = 1;

    public static boolean isTestMode() {
        return TEST_MODE;
    }

    public static void setTestMode(boolean value) {
        TEST_MODE = value;
    }

    public static int getCurrentStage() {
        return CurrentStage;
    }

    public static final int CORRECT_ANSWER = 0;
    public static final int WRONG_ANSWER = 1;

    public static final String TEST_COMMAND = "0915";




}
