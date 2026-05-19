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
import java.util.TreeSet;

class FavoriteChannels {

    private final TreeSet<Integer> favorites = new TreeSet<>();

    void toggle(int channel) {
        if (favorites.contains(channel)) {
            favorites.remove(channel);
        } else {
            favorites.add(channel);
        }
    }

    Optional<Integer> nextAfter(int currentChannel) {
        for (int favorite : favorites) {
            if (favorite > currentChannel) {
                return Optional.of(favorite);
            }
        }
        if (!favorites.isEmpty()) {
            return Optional.of(favorites.first());
        }
        return Optional.empty();
    }
}
