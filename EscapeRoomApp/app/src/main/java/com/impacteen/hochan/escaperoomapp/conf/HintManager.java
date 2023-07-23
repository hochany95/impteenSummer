package com.impacteen.hochan.escaperoomapp.conf;

import java.util.ArrayList;

public class HintManager {

    public static ArrayList<Boolean> opened = new ArrayList<>();

    public static void init() {
        for (int i = 0; i < 10; i++) {
            opened.add(false);
        }
    }
}
