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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ScannedChannelList {

    private static final int MAX_SCAN_ATTEMPTS = 100;

    private final List<Integer> channels = new ArrayList<>();

    boolean isEmpty() {
        return channels.isEmpty();
    }

    void scanAll(Tuner tuner) {
        channels.clear();
        Set<Integer> seen = new HashSet<>();
        seen.add(parseChannel(tuner.getCurrentCH()));

        for (int i = 0; i < MAX_SCAN_ATTEMPTS; i++) {
            int found = parseChannel(tuner.seekCH());
            if (seen.contains(found)) {
                break;
            }
            seen.add(found);
            channels.add(found);
        }
        Collections.sort(channels);
    }

    int channelUp(int currentChannel) {
        return navigate(currentChannel, true);
    }

    int channelDown(int currentChannel) {
        return navigate(currentChannel, false);
    }

    private int navigate(int currentChannel, boolean up) {
        if (channels.isEmpty()) {
            return currentChannel;
        }
        if (up) {
            for (int channel : channels) {
                if (channel > currentChannel) {
                    return channel;
                }
            }
            return channels.get(0);
        }
        for (int i = channels.size() - 1; i >= 0; i--) {
            if (channels.get(i) < currentChannel) {
                return channels.get(i);
            }
        }
        return channels.get(channels.size() - 1);
    }

    private static int parseChannel(String channel) {
        return Integer.parseInt(channel);
    }
}
