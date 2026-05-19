package com.bestreviewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TVControllerTest {

    @Mock
    private Tuner tuner;

    private TVController controller;
    private final AtomicReference<String> currentChannel = new AtomicReference<>("0");

    @BeforeEach
    void setUp() {
        controller = new TVController(tuner);
    }

    private void stubChannelTracking() {
        when(tuner.getCurrentCH()).thenAnswer(invocation -> currentChannel.get());
        doAnswer(invocation -> {
            currentChannel.set(invocation.getArgument(0));
            return null;
        }).when(tuner).setCH(anyString());
    }

  // --- 숫자·확인 (N-01 ~ N-07) ---

    @Test
    @DisplayName("N-01: 숫자 1 입력 후 확인 시 1번 채널로 설정")
    void setChannel1WhenKey1AndOk() {
        controller.pushButton(remoteKey.KEY_1);
        controller.pushButton(remoteKey.KEY_OK);

        verify(tuner).setCH("1");
    }

    @Test
    @DisplayName("N-02: 1, 2 연속 입력 시 확인 없이 12번 채널로 설정")
    void setChannel12WhenKey1AndKey2() {
        controller.pushButton(remoteKey.KEY_1);
        controller.pushButton(remoteKey.KEY_2);

        verify(tuner).setCH("12");
    }

    @Test
    @DisplayName("N-03: 1,2,3,4 연속 입력 시 12번 후 34번 채널로 설정")
    void setChannel12Then34() {
        controller.pushButton(remoteKey.KEY_1);
        controller.pushButton(remoteKey.KEY_2);
        controller.pushButton(remoteKey.KEY_3);
        controller.pushButton(remoteKey.KEY_4);

        InOrder inOrder = inOrder(tuner);
        inOrder.verify(tuner).setCH("12");
        inOrder.verify(tuner).setCH("34");
    }

    @Test
    @DisplayName("N-04: 4,5,6 연속 입력 시 45번 채널로 설정")
    void setChannel45WhenKey456() {
        controller.pushButton(remoteKey.KEY_4);
        controller.pushButton(remoteKey.KEY_5);
        controller.pushButton(remoteKey.KEY_6);

        verify(tuner).setCH("45");
    }

    @Test
    @DisplayName("N-05: 4,5,6 이후 6과 확인 입력 시 6번 채널로 설정")
    void setChannel6After456AndOk() {
        controller.pushButton(remoteKey.KEY_4);
        controller.pushButton(remoteKey.KEY_5);
        controller.pushButton(remoteKey.KEY_6);
        controller.pushButton(remoteKey.KEY_OK);

        InOrder inOrder = inOrder(tuner);
        inOrder.verify(tuner).setCH("45");
        inOrder.verify(tuner).setCH("6");
    }

    @Test
    @DisplayName("N-06: 4,5,6 이후 채널 업 입력 시 미확인 6 무효화 후 46번 채널")
    void invalidatePendingDigitOnChannelUp() {
        stubChannelTracking();

        controller.pushButton(remoteKey.KEY_4);
        controller.pushButton(remoteKey.KEY_5);
        controller.pushButton(remoteKey.KEY_6);
        controller.pushButton(remoteKey.KEY_CH_UP);

        InOrder inOrder = inOrder(tuner);
        inOrder.verify(tuner).setCH("45");
        inOrder.verify(tuner).setCH("46");
        verify(tuner, never()).setCH("6");
    }

    @Test
    @DisplayName("N-07: 0, 7 입력 시 7번 채널로 설정")
    void setChannel7WhenKey0AndKey7() {
        controller.pushButton(remoteKey.KEY_0);
        controller.pushButton(remoteKey.KEY_7);

        verify(tuner).setCH("7");
    }

    @Test
    @DisplayName("확인 시 버퍼가 비어 있으면 튜너를 변경하지 않음")
    void doNotSetChannelWhenOkWithEmptyBuffer() {
        controller.pushButton(remoteKey.KEY_OK);

        verify(tuner, never()).setCH(anyString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    @DisplayName("B-KEY: 단일 숫자 입력 후 확인 시 해당 채널로 설정")
    void setChannelForSingleDigitWithOk(int digit) {
        pushDigit(digit);
        controller.pushButton(remoteKey.KEY_OK);

        verify(tuner).setCH(String.valueOf(digit));
    }

    @Test
    @DisplayName("B-CH-03: 9, 8 연속 입력 시 98번 채널로 설정")
    void setChannel98WhenKey9AndKey8() {
        controller.pushButton(remoteKey.KEY_9);
        controller.pushButton(remoteKey.KEY_8);

        verify(tuner).setCH("98");
    }

    @Test
    @DisplayName("B-CH-04: 9, 9 연속 입력 시 99번 채널로 설정")
    void setChannel99WhenKey9AndKey9() {
        controller.pushButton(remoteKey.KEY_9);
        controller.pushButton(remoteKey.KEY_9);

        verify(tuner).setCH("99");
    }

    @Test
    @DisplayName("B-KEY-05: 99 설정 후 확인 시 추가 변경 없음")
    void okAfter99DoesNotChangeChannelAgain() {
        controller.pushButton(remoteKey.KEY_9);
        controller.pushButton(remoteKey.KEY_9);
        controller.pushButton(remoteKey.KEY_OK);

        verify(tuner).setCH("99");
        verify(tuner, times(1)).setCH(anyString());
    }

  // --- 업/다운 (검색 없음 UD-01 ~ UD-04) ---

    @ParameterizedTest
    @CsvSource({"6, 7", "99, 0"})
    @DisplayName("UD: 검색 없을 때 채널 업")
    void channelUpWithoutScan(String current, String expected) {
        stubChannelTracking();
        currentChannel.set(current);

        controller.pushButton(remoteKey.KEY_CH_UP);

        verify(tuner).setCH(expected);
    }

    @ParameterizedTest
    @CsvSource({"6, 5", "0, 99"})
    @DisplayName("UD: 검색 없을 때 채널 다운")
    void channelDownWithoutScan(String current, String expected) {
        stubChannelTracking();
        currentChannel.set(current);

        controller.pushButton(remoteKey.KEY_CH_DOWN);

        verify(tuner).setCH(expected);
    }

  // --- 채널 검색 및 목록 기준 업/다운 (S-01, UD-05 ~ UD-08) ---

    @Test
    @DisplayName("S-01: 채널 검색 시 seekCH 호출")
    void searchChannelsCallsSeek() {
        when(tuner.getCurrentCH()).thenReturn("6");
        when(tuner.seekCH()).thenReturn("14", "4", "6");

        controller.pushButton(remoteKey.KEY_SEARCH);

        verify(tuner, times(3)).seekCH();
    }

    @Test
    @DisplayName("UD-05~06: 검색 목록 있을 때 6번 시청 중 업/다운")
    void channelUpDownWithScannedList() {
        stubChannelTracking();
        currentChannel.set("6");
        when(tuner.seekCH()).thenReturn("14", "4", "6");

        controller.pushButton(remoteKey.KEY_SEARCH);

        controller.pushButton(remoteKey.KEY_CH_UP);
        verify(tuner).setCH("14");

        currentChannel.set("6");
        controller.pushButton(remoteKey.KEY_CH_DOWN);
        verify(tuner).setCH("4");
    }

    @Test
    @DisplayName("UD-07~08: 검색 목록 있을 때 목록 밖 채널에서 업/다운")
    void channelUpDownOutsideScannedList() {
        stubChannelTracking();
        currentChannel.set("15");
        when(tuner.seekCH()).thenReturn("4", "6", "14", "4");

        controller.pushButton(remoteKey.KEY_SEARCH);

        controller.pushButton(remoteKey.KEY_CH_UP);
        verify(tuner).setCH("4");

        currentChannel.set("15");
        controller.pushButton(remoteKey.KEY_CH_DOWN);
        verify(tuner).setCH("14");
    }

  // --- 선호 채널 (P-01, P-02) ---

    @Test
    @DisplayName("P-01: 선호 1,4,12,56 / 현재 6 / 다음선호 → 12")
    void nextFavoriteFrom6() {
        stubChannelTracking();
        addFavorites(1, 4, 12, 56);
        currentChannel.set("6");

        controller.pushButton(remoteKey.KEY_FAV_NEXT);

        verify(tuner).setCH("12");
    }

    @Test
    @DisplayName("P-02: 선호 1,4,12,56 / 현재 56 / 다음선호 → 1")
    void nextFavoriteRotatesFrom56() {
        stubChannelTracking();
        addFavorites(1, 4, 12, 56);
        currentChannel.set("56");

        controller.pushButton(remoteKey.KEY_FAV_NEXT);

        verify(tuner).setCH("1");
    }

    @Test
    @DisplayName("F-01: 비선호 채널 시청 중 선호추가 후 다음선호로 해당 채널 이동")
    void addFavoriteThenNextFavoriteGoesToAddedChannel() {
        stubChannelTracking();
        currentChannel.set("6");
        controller.pushButton(remoteKey.KEY_FAV_ADD);
        currentChannel.set("5");

        controller.pushButton(remoteKey.KEY_FAV_NEXT);

        verify(tuner).setCH("6");
    }

    @Test
    @DisplayName("F-02: 선호 채널 재입력 시 삭제되어 다음선호에서 제외")
    void removeFavoriteOnSecondFavAdd() {
        stubChannelTracking();
        addFavorites(1, 4, 12, 56);
        currentChannel.set("6");
        controller.pushButton(remoteKey.KEY_FAV_ADD);
        controller.pushButton(remoteKey.KEY_FAV_ADD);
        currentChannel.set("5");

        controller.pushButton(remoteKey.KEY_FAV_NEXT);

        verify(tuner).setCH("12");
        verify(tuner, never()).setCH("6");
    }

    @Test
    @DisplayName("P-03: 선호 1개일 때 다음선호는 동일 채널로 로테이션")
    void nextFavoriteRotatesWhenOnlyOneFavorite() {
        stubChannelTracking();
        currentChannel.set("7");
        controller.pushButton(remoteKey.KEY_FAV_ADD);

        controller.pushButton(remoteKey.KEY_FAV_NEXT);

        verify(tuner).setCH("7");
    }

    @Test
    @DisplayName("P-04: 선호 없을 때 다음선호는 튜너를 변경하지 않음")
    void nextFavoriteDoesNothingWhenEmpty() {
        when(tuner.getCurrentCH()).thenReturn("6");

        controller.pushButton(remoteKey.KEY_FAV_NEXT);

        verify(tuner, never()).setCH(anyString());
    }

    @Test
    @DisplayName("X-01: 숫자 버퍼 대기 중 선호추가 시 버퍼 클리어")
    void clearBufferOnFavoriteAddWhileDigitsPending() {
        when(tuner.getCurrentCH()).thenReturn("45");

        controller.pushButton(remoteKey.KEY_4);
        controller.pushButton(remoteKey.KEY_FAV_ADD);
        controller.pushButton(remoteKey.KEY_OK);

        verify(tuner, never()).setCH(anyString());
    }

    private void pushDigit(int digit) {
        controller.pushButton(remoteKey.valueOf("KEY_" + digit));
    }

    private void addFavorites(int... channels) {
        for (int channel : channels) {
            currentChannel.set(String.valueOf(channel));
            controller.pushButton(remoteKey.KEY_FAV_ADD);
        }
    }
}
