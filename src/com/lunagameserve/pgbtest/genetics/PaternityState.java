package com.lunagameserve.pgbtest.genetics;

/**
 * Created by sixstring982 on 1/18/16.
 */
public enum PaternityState {
    FADE_IN(1),
    MATCH_ONE(0),
    FLASH_ONE(1),
    MATCH_TWO(0),
    FLASH_TWO(1),
    MATCH_THREE(0),
    FLASH_THREE(1),
    FADE_OUT(1);

    private static final int BASE_FRAME_LENGTH = 100;
    private final int length;

    private PaternityState(int lengthMultiplier) {
        this.length = BASE_FRAME_LENGTH * lengthMultiplier;
    }

    public int getLength() {
        return this.length;
    }

    public static PaternityState getNext(PaternityState state) {
        switch (state) {
            case FADE_IN: return MATCH_ONE;
            case MATCH_ONE: return FLASH_ONE;
            case FLASH_ONE: return MATCH_TWO;
            case MATCH_TWO: return FLASH_TWO;
            case FLASH_TWO: return MATCH_THREE;
            case MATCH_THREE: return FLASH_THREE;
            case FLASH_THREE: return FADE_OUT;
            case FADE_OUT: return FADE_IN;
            default: throw new IllegalArgumentException();
        }
    }
}
