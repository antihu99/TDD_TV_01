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

import java.util.Optional;
import java.util.function.Consumer;

public class ChannelInputBuffer {

    private String digits = "";
    private final Consumer<Integer> onChannelReady;

    public ChannelInputBuffer(Consumer<Integer> onChannelReady) {
        this.onChannelReady = onChannelReady;
    }

    public void appendDigit(String digit) {
        digits += digit;
        flushCompletePairs();
    }

    public void clear() {
        digits = "";
    }

    public boolean isEmpty() {
        return digits.isEmpty();
    }

    public Optional<Integer> parseValidChannel() {
        if (digits.isEmpty()) {
            return Optional.empty();
        }
        return parseChannelValue(digits);
    }

    private void flushCompletePairs() {
        while (digits.length() >= 2) {
            String pair = digits.substring(0, 2);
            parseChannelValue(pair).ifPresent(onChannelReady);
            digits = digits.substring(2);
        }
    }

    private static Optional<Integer> parseChannelValue(String value) {
        try {
            int channel = Integer.parseInt(value);
            if (ChannelConstants.isValid(channel)) {
                return Optional.of(channel);
            }
        } catch (NumberFormatException ignored) {
            // invalid buffer
        }
        return Optional.empty();
    }
}
