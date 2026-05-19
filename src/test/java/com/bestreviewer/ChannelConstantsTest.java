package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChannelConstantsTest {

    @ParameterizedTest
    @CsvSource({
            "-1, false",
            "0, true",
            "1, true",
            "98, true",
            "99, true",
            "100, false"
    })
    @DisplayName("채널 유효 범위 0~99 검증")
    void isValidChannelRange(int channel, boolean expected) {
        assertEquals(expected, ChannelConstants.isValid(channel));
    }
}
