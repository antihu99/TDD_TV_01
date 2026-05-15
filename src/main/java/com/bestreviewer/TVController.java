/**
 * Copyright 2020 by Samsung Electronics, Inc.,
 *
 * This software is the confidential and proprietary information
 * of Samsung Electronics, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Samsung.
 */

package com.bestreviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Collections;

public class TvController {

    private Tuner tuner;
    private String numberBuffer;

    private final TreeSet<Integer> favoriteChannels = new TreeSet<>();

    private final List<Integer> scannedChannels = new ArrayList<>();

    public TvController(Tuner tuner) {
        this.tuner = tuner;
        numberBuffer = "";
    }

    public void pushButton(RemoteKey key) {
        switch (key) {
            case KEY_0:
            case KEY_1:
            case KEY_2:
            case KEY_3:
            case KEY_4:
            case KEY_5:
            case KEY_6:
            case KEY_7:
            case KEY_8:
            case KEY_9:
                handleNumberKey(key);
                break;

            case KEY_OK:
                confirmNumber();
                break;

            case KEY_CHANNEL_UP:
                moveUp();
                clearBuffer();
                break;

            case KEY_CHANNEL_DOWN:
                moveDown();
                clearBuffer();
                break;

            case KEY_SEARCH:
                searchChannels();
                clearBuffer();
                break;

            case KEY_FAVORITE_ADD:
                toggleFavorite();
                clearBuffer();
                break;

            case KEY_NEXT_FAVORITE:
                nextFavorite();
                clearBuffer();
                break;
        }
    }

    private void setTunerCh() {
        // 로그는 테스트의 결과가 절대 아닙니다. 로그가 있는 것을 테스트로 간주하지 마시기 바랍니다.
        System.out.println("현재 설정하는 채널 : " + numberBuffer);
        // tuner.setCH(numberBuffer);
    }

    private void handleNumberKey(RemoteKey key) {

        int value = extractNumber(key);

        numberBuffer += value;

        if (numberBuffer.length() == 2) {
            int ch = Integer.parseInt(numberBuffer);
            tuner.setCH(ch);
            numberBuffer = "";
        } else if (numberBuffer.length() > 2) {
            numberBuffer = String.valueOf(value);
        }
    }

    private int extractNumber(RemoteKey key) {
        return Integer.parseInt(key.name().replace("NUM_", ""));
    }

    private void confirmNumber() {

        if (numberBuffer.isEmpty()) {
            return;
        }

        int ch = Integer.parseInt(numberBuffer);

        tuner.setCH(ch);

        clearBuffer();
    }

    private void clearBuffer() {
        numberBuffer = "";
    }

    private void toggleFavorite() {

        int current = tuner.getCurrentCH();

        if (favoriteChannels.contains(current)) {
            favoriteChannels.remove(current);
        } else {
            favoriteChannels.add(current);
        }
    }

    private void nextFavorite() {

        if (favoriteChannels.isEmpty()) {
            return;
        }

    }

}
