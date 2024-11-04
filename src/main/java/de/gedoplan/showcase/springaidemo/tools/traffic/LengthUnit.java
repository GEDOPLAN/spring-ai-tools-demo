package de.gedoplan.showcase.springaidemo.tools.traffic;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LengthUnit {
    KILOMETRES, MILES;

    @JsonCreator
    public static LengthUnit safeValueOf(String string) {
        try {
            return LengthUnit.valueOf(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
