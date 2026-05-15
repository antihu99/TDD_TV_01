package com.bestreviewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.mock;

class TunerTest {
    Tuner tuner;

    @BeforeEach
    void setup() {
        tuner = mock(Tuner.class);
    }

    @Test
    @DisplayName("초기 채널값 확인 테스트")
    void initChannel() {
        int initCh = Integer.parseInt(tuner.getCurrentCH());
        assertTrue(initCh >= 0 && initCh <= 99);
    }

    @ParameterizedTest
    @CsvSource({"0", "4", "5", "12", "99"})
    @DisplayName("채널설정테스트 : 유효한 채널인 경우")
    void testSetChForValidChannel(String channel) {
        tuner.setCH(channel);
        assertEquals(channel, tuner.getCurrentCH());
    }
    @ParameterizedTest
    @CsvSource({"-12", "-2", "-0", "100", "9999"})
    @DisplayName("채널설정테스트 : 유효하지 않은 채널인 경우")
    void testSetChForInvalidChannel(String channel) {
        assertThrows(IllegalArgumentException.class, () -> tuner.setCH(channel));
    }

    @Test
    @DisplayName("채널검색테스트 : 시작 채널로부터 10개 채널 검색 테스트")
    void testSeekCh10times() {
        ArrayList<String> seekChannel = new ArrayList<>();
        for (int i=0; i<10; i++){
            String seekCh = tuner.seekCH();
            assumeTrue(seekCh != null);
            int ch = Integer.parseInt(seekCh);
            assertTrue(0<= ch && ch <=99);
            seekChannel.add(seekCh);
        }
        assertEquals(10, seekChannel.size());
    }

    @Test
    @DisplayName("채널검색테스트 : 시작 채널값을 99로 지정하고 10개 채널 검색 테스트")
    void testSeekCh10timesAfterSetCH() {
        ArrayList<String> seekChannel = new ArrayList<>();
        tuner.setCH("99");
        for (int i=0; i<10; i++){
            String seekCh = tuner.seekCH();
            assumeTrue(seekCh != null);
            int ch = Integer.parseInt(seekCh);
            assertTrue(0<= ch && ch <=99);
            seekChannel.add(seekCh);
        }
        assertEquals(10, seekChannel.size());
    }


}