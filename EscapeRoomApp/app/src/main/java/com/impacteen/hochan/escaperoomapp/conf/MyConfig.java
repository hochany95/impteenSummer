package com.impacteen.hochan.escaperoomapp.conf;

public class MyConfig {

    public static boolean testMode = false;
    public static int openedState = 0;

    public static boolean isTestMode() {
        return testMode;
    }

    public static void setTestMode(boolean value) {
        testMode = value;
    }

    public static int getOpenedStage() {
        return openedState;
    }

    public static final int CORRECT_ANSWER = 0;
    public static final int WRONG_ANSWER = 1;


}
