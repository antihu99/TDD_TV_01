package com.bestreviewer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Golden Master용 Tuner: 채널 상태를 유지하고 setCH/seekCH 호출을 기록한다.
 */
final class RecordingTuner implements Tuner {

    private final List<String> trace = new ArrayList<>();
    private final Deque<String> seekQueue = new ArrayDeque<>();
    private String currentChannel = "0";

    void setInitialChannel(String channel) {
        currentChannel = channel;
        trace.add("# initial_ch=" + channel);
    }

    void prepareChannel(String channel) {
        currentChannel = channel;
        trace.add("# setup_ch=" + channel);
    }

    void enqueueSeekResponses(String... channels) {
        seekQueue.addAll(Arrays.asList(channels));
    }

    List<String> getTrace() {
        return trace;
    }

    void appendFinalChannel() {
        trace.add("# final_ch=" + currentChannel);
    }

    @Override
    public String seekCH() {
        if (seekQueue.isEmpty()) {
            throw new IllegalStateException("seekCH() called without queued response for ch=" + currentChannel);
        }
        String found = seekQueue.removeFirst();
        trace.add("seekCH() -> " + found);
        currentChannel = found;
        return found;
    }

    @Override
    public void setCH(String ch) {
        trace.add("setCH(" + ch + ")");
        currentChannel = ch;
    }

    @Override
    public String getCurrentCH() {
        return currentChannel;
    }
}
