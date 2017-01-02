package com.Luguan.Mroff.util;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Debug {

    public static List<Vector> checkedBoxes = new ArrayList<Vector>();
    private static boolean isEnabled;

    public static void addCheckedBox(float x, float y, float width, float height, Color color) {
        if (isEnabled) {
            checkedBoxes.add(new Vector(x, y, width, height, color));
        }
    }

    public static void setEnabled(boolean enabled) {
        if (!Debug.isEnabled && enabled) {
            checkedBoxes.clear();
        }
        Debug.isEnabled = enabled;
    }

    public static class Vector {
        public float x, y;
        public float width, height;
        public Color color;


        public Vector(float x, float y, float width, float height, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
        }
    }
}
