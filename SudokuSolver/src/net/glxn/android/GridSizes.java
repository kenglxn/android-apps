package net.glxn.android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ken
 * Date: 29.03.11
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public enum GridSizes {
    NINE_BY_NINE("9 x 9");

    private String value;

    GridSizes(String s) {
        this.value = s;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CharSequence[] charSequences() {
        GridSizes[] gridSizes = GridSizes.values();
        CharSequence[] charSequences = new CharSequence[gridSizes.length];
        for (int i = 0; i < gridSizes.length; i++) {
            charSequences[i] = gridSizes[i].value;
        }
        return charSequences;
    }

    public static CharSequence charSequenceAt(int i) {
        return charSequences()[i];
    }
}
