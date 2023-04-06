package ru.netology.graphics.image;

import java.util.Arrays;


public class ColorSchema implements TextColorSchema {
//   char symbols[] = new char[] {'#', '$', '@', '%', '*', '+', '-','`'};
//
//    @Override
//    public char convert(int color) { return symbols[(int) Math.floor(color / 256 * symbols.length)]; }

    public char convert(int color) {
        if (color >= 0 && color <= 31) {
            return '#';
        } else if (color >= 32 && color <= 63) {
            return '$';
        }
        if (color >= 64 && color <= 95) {
            return '@';
        } else if (color >= 96 && color <= 127) {
            return '%';
        }
        else if (color >= 128 && color <= 159) {
            return '*';
        }
        else if (color >= 160 && color <= 191) {
            return '+';
        }
        else if (color >= 192 && color <= 223) {
            return '-';
        }
        else return '`';
    }
}