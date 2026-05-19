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

import java.util.EnumMap;
import java.util.Map;

public class TVController {

    private final Tuner tuner;
    private final ChannelInputBuffer channelBuffer;
    private final FavoriteChannels favoriteChannels;
    private final ScannedChannelList scannedChannels;
    private final Map<remoteKey, RemoteKeyHandler> keyHandlers;

    public TVController(Tuner tuner) {
        this.tuner = tuner;
        this.channelBuffer = new ChannelInputBuffer(channel -> applyChannel(channel));
        this.favoriteChannels = new FavoriteChannels();
        this.scannedChannels = new ScannedChannelList();
        this.keyHandlers = createKeyHandlers();
    }

    public void pushButton(remoteKey key) {
        if (key.isDigitKey()) {
            channelBuffer.appendDigit(key.getKeyValue());
            return;
        }

        if (key == remoteKey.KEY_OK) {
            confirmChannelFromBuffer();
            return;
        }

        channelBuffer.clear();

        RemoteKeyHandler handler = keyHandlers.get(key);
        if (handler != null) {
            handler.handle(this);
        }
    }

    void confirmChannelFromBuffer() {
        channelBuffer.parseValidChannel().ifPresent(channel -> {
            applyChannel(channel);
            channelBuffer.clear();
        });
    }

    void channelUp() {
        int current = getCurrentChannel();
        int next = scannedChannels.isEmpty()
                ? wrapChannel(current + 1)
                : scannedChannels.channelUp(current);
        tuner.setCH(String.valueOf(next));
    }

    void channelDown() {
        int current = getCurrentChannel();
        int next = scannedChannels.isEmpty()
                ? wrapChannel(current - 1)
                : scannedChannels.channelDown(current);
        tuner.setCH(String.valueOf(next));
    }

    void searchChannels() {
        scannedChannels.scanAll(tuner);
    }

    void toggleFavoriteChannel() {
        favoriteChannels.toggle(getCurrentChannel());
    }

    void nextFavoriteChannel() {
        favoriteChannels.nextAfter(getCurrentChannel()).ifPresent(this::applyChannel);
    }

    private void applyChannel(int channel) {
        tuner.setCH(String.valueOf(channel));
    }

    private int getCurrentChannel() {
        return Integer.parseInt(tuner.getCurrentCH());
    }

    private static int wrapChannel(int channel) {
        if (channel > ChannelConstants.MAX_CHANNEL) {
            return ChannelConstants.MIN_CHANNEL;
        }
        if (channel < ChannelConstants.MIN_CHANNEL) {
            return ChannelConstants.MAX_CHANNEL;
        }
        return channel;
    }

    private static Map<remoteKey, RemoteKeyHandler> createKeyHandlers() {
        Map<remoteKey, RemoteKeyHandler> handlers = new EnumMap<>(remoteKey.class);
        handlers.put(remoteKey.KEY_CH_UP, TVController::channelUp);
        handlers.put(remoteKey.KEY_CH_DOWN, TVController::channelDown);
        handlers.put(remoteKey.KEY_SEARCH, TVController::searchChannels);
        handlers.put(remoteKey.KEY_FAV_ADD, TVController::toggleFavoriteChannel);
        handlers.put(remoteKey.KEY_FAV_NEXT, TVController::nextFavoriteChannel);
        return handlers;
    }
}
