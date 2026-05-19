package com.bestreviewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChannelInputBufferTest {

    private final List<Integer> appliedChannels = new ArrayList<>();
    private ChannelInputBuffer buffer;

    @BeforeEach
    void setUp() {
        appliedChannels.clear();
        buffer = new ChannelInputBuffer(appliedChannels::add);
    }

    @Test
    @DisplayName("B-CH-04: 9,9 입력 시 99번 채널 즉시 반영")
    void flush99OnDoubleNine() {
        buffer.appendDigit("9");
        buffer.appendDigit("9");

        assertEquals(Collections.singletonList(99), appliedChannels);
        assertTrue(buffer.isEmpty());
    }

    @Test
    @DisplayName("B-CH-03: 9,8 입력 시 98번 채널 즉시 반영")
    void flush98OnNineEight() {
        buffer.appendDigit("9");
        buffer.appendDigit("8");

        assertEquals(Collections.singletonList(98), appliedChannels);
    }

    @Test
    @DisplayName("B-CH-01: 단일 0과 확인 파싱 시 0번 채널")
    void parseSingleZero() {
        buffer.appendDigit("0");

        Optional<Integer> channel = buffer.parseValidChannel();

        assertTrue(channel.isPresent());
        assertEquals(0, channel.get().intValue());
    }

    @Test
    @DisplayName("B-INV-02: 빈 버퍼 파싱 시 empty")
    void parseEmptyBufferReturnsEmpty() {
        assertTrue(buffer.parseValidChannel().isEmpty());
    }

    @Test
    @DisplayName("B-INV-01: 1,0,0 입력 시 10 즉시 반영 후 잔여 0만 파싱 가능")
    void hundredInputDecomposesToTenAndZero() {
        buffer.appendDigit("1");
        buffer.appendDigit("0");
        buffer.appendDigit("0");

        assertEquals(Collections.singletonList(10), appliedChannels);
        assertEquals(Optional.of(0), buffer.parseValidChannel());
    }

    @Test
    @DisplayName("clear 후 버퍼는 비어 있음")
    void clearEmptiesBuffer() {
        buffer.appendDigit("4");
        buffer.clear();

        assertTrue(buffer.isEmpty());
        assertTrue(buffer.parseValidChannel().isEmpty());
    }

    @Test
    @DisplayName("N-07: 0,7 입력 시 7번만 반영")
    void leadingZeroIgnoredForSeven() {
        buffer.appendDigit("0");
        buffer.appendDigit("7");

        assertEquals(Collections.singletonList(7), appliedChannels);
    }
}
