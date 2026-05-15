package com.bestreviewer;

import java.util.ArrayList;
import java.util.List;

public class FakeTuner implements Tuner {

    private int currentCH;

    private final List<Integer> availableChannels;

    public FakeTuner(List<Integer> availableChannels) {
        this.availableChannels = availableChannels;
        this.currentCH = 0;
    }

    @Override
    public int seekCH() {

        for (int i = currentCH + 1; i <= 99; i++) {
            if (availableChannels.contains(i)) {
                currentCH = i;
                return currentCH;
            }
        }

        for (int i = 0; i <= currentCH; i++) {
            if (availableChannels.contains(i)) {
                currentCH = i;
                return currentCH;
            }
        }

        return currentCH;
    }

    @Override
    public void setCH(int ch) {
        currentCH = ch;
    }

    @Override
    public int getCurrentCH() {
        return currentCH;
    }
}
