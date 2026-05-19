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

public final class ChannelConstants {

    public static final int MIN_CHANNEL = 0;
    public static final int MAX_CHANNEL = 99;

    private ChannelConstants() {
    }

    public static boolean isValid(int channel) {
        return channel >= MIN_CHANNEL && channel <= MAX_CHANNEL;
    }
}
