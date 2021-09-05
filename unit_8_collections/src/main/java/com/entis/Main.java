package com.entis;

import com.entis.controller.ConsoleMenu;
import com.entis.controller.MathSetController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        MathSetController controller=new MathSetController();
        ConsoleMenu menu=new ConsoleMenu(controller,"initializeMathSet","add","join","intersection","sortDesc","sortAsc","get",
                "getMax","getMin","getAverage","getMedian","cut","clear","printSet");
        try {
            menu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
