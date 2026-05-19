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

enum remoteKey {
    KEY_0("0", true),
    KEY_1("1", true),
    KEY_2("2", true),
    KEY_3("3", true),
    KEY_4("4", true),
    KEY_5("5", true),
    KEY_6("6", true),
    KEY_7("7", true),
    KEY_8("8", true),
    KEY_9("9", true),
    KEY_OK("OK", false),
    KEY_CH_UP("CH_UP", false),
    KEY_CH_DOWN("CH_DOWN", false),
    KEY_SEARCH("SEARCH", false),
    KEY_FAV_ADD("FAV_ADD", false),
    KEY_FAV_NEXT("FAV_NEXT", false);

    private final String keyValue;
    private final boolean digitKey;

    remoteKey(String keyValue, boolean digitKey) {
        this.keyValue = keyValue;
        this.digitKey = digitKey;
    }

    public boolean isDigitKey() {
        return digitKey;
    }

    public String getKeyValue() {
        return keyValue;
    }

    @Override
    public String toString() {
        return keyValue;
    }
}
