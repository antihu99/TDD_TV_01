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

enum RemoteKey {
    KEY_1("1"),
    KEY_2("2"),
    KEY_3("3"),
    KEY_4("4"),
    KEY_5("5"),
    KEY_6("6"),
    KEY_7("7"),
    KEY_8("8"),
    KEY_9("9"),
    KEY_0("0"),
    KEY_OK("OK"),
    KEY_CHANNEL_UP("CHANNEL_UP"),
    KEY_CHANNEL_DOWN("CHANNEL_DOWN"),
    KEY_SEARCH("SEARCH"),
    KEY_FAVORITE_ADD("FAVORITE_ADD"),
    KEY_NEXT_FAVORITE("NEXT_FAVORITE"),;

    final private String key;

    RemoteKey(String key) {
        this.key = key;
    }

    public String toString() {
        return key;
    }
}
