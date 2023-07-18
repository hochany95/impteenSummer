package com.impacteen.hochan.escaperoomapp.conf;

import java.util.ArrayList;
import java.util.Arrays;

public class Hint {

    public static ArrayList<Boolean> opened = new ArrayList<>();

    public static String hintInit = "시계나 숫자가 아닌 전체 그림을 한 번에 보자\n" +
            "5개의 o에 각각 들어갈 글자가 무엇일까?\n" +
            "답은 영어이다";

    public static String hint1 = "강조하고 있는 단어들을 자세히 살펴보자\n" +
            "각 단어들이 무엇을 포함하고 있을까\n";
    public static String hint2 = "1070를 주목하자\n" +
            "방 안 어딘가에 해당 숫자를 포함하는 자료가 있다\n"+
            "각 숫자를 조합해서 받을 수 있는 돈이 얼마일까?";
    public static String hint3 = "거울로 통해 보이는 것이 있는가?\n"
            + "고장난 시계를 주목하자.\n"
            +" 몇 시 일까?";
    public static String hint4 = "화면에 보이는 카드를 주목하자\n"
            +"방 안 어딘가에 나머지 반쪽이 있을 것이다.\n"
            +"나눠진 카드가 하나가 되었을 때, 어떤 숫자가 보이는가?";
    public static String hint5 = "문 앞에 음료를 받았는가?\n"
            +"일단 음료를 마시며 잠시 쉬자\n"
            +"그리고 음료 안을 확인해 보자";
    public static String hint6 = "비밀 펜을 찾았는가?\n"
            +"uv 라이트로만 볼 수 있는 문제가 있다.\n"
            +" 아직 문제는 미정...!\n";
    public static String hint7 = "아름다운 노래를 ";
    public static String hint8 = "8번째 힌트입니다.";
    public static String hint9 = "9번째 힌트입니다.";

    public static void init() {
        for (int i = 0; i < 10; i++) {
            opened.add(false);
        }
    }
}
