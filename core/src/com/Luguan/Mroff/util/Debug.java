package com.Luguan.Mroff.util;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Debug {

    public static List<Vector> checkedBoxes = new ArrayList<Vector>();

    public static void addCheckedBox(float x, float y, float width, float height, Color color) {
        checkedBoxes.add(new Vector(x, y, width, height, color));
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
