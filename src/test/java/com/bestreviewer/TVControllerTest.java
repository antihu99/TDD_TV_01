package com.bestreviewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
@DisplayName("TVController — 리모컨 입력 통합 단위 테스트")
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

    private void pushDigit(int digit) {
        controller.pushButton(remoteKey.valueOf("KEY_" + digit));
    }

    private void addFavorites(int... channels) {
        for (int channel : channels) {
            currentChannel.set(String.valueOf(channel));
            controller.pushButton(remoteKey.KEY_FAV_ADD);
        }
    }

    @Nested
    @DisplayName("1. 숫자·확인 입력")
    class NumberAndConfirm {

        @Test
        @DisplayName("N-01: Given 초기 — When 1·확인 — Then 1번 채널")
        void n01_singleDigitWithOk() {
            // Given: 초기 상태

            // When
            controller.pushButton(remoteKey.KEY_1);
            controller.pushButton(remoteKey.KEY_OK);

            // Then
            verify(tuner).setCH("1");
        }

        @Test
        @DisplayName("N-02: Given 초기 — When 1·2 연속 — Then 12번 (확인 없이)")
        void n02_twoDigitsAutoConfirm() {
            controller.pushButton(remoteKey.KEY_1);
            controller.pushButton(remoteKey.KEY_2);

            verify(tuner).setCH("12");
        }

        @Test
        @DisplayName("N-03: Given 초기 — When 1·2·3·4 — Then 12 후 34번")
        void n03_fourDigitsTwoPairs() {
            controller.pushButton(remoteKey.KEY_1);
            controller.pushButton(remoteKey.KEY_2);
            controller.pushButton(remoteKey.KEY_3);
            controller.pushButton(remoteKey.KEY_4);

            InOrder inOrder = inOrder(tuner);
            inOrder.verify(tuner).setCH("12");
            inOrder.verify(tuner).setCH("34");
        }

        @Test
        @DisplayName("N-04: Given 초기 — When 4·5·6 — Then 45번")
        void n04_threeDigitsFirstPair() {
            controller.pushButton(remoteKey.KEY_4);
            controller.pushButton(remoteKey.KEY_5);
            controller.pushButton(remoteKey.KEY_6);

            verify(tuner).setCH("45");
        }

        @Test
        @DisplayName("N-05: Given 456 입력 후 — When 확인 — Then 45 후 6번")
        void n05_pendingDigitConfirmedWithOk() {
            controller.pushButton(remoteKey.KEY_4);
            controller.pushButton(remoteKey.KEY_5);
            controller.pushButton(remoteKey.KEY_6);
            controller.pushButton(remoteKey.KEY_OK);

            InOrder inOrder = inOrder(tuner);
            inOrder.verify(tuner).setCH("45");
            inOrder.verify(tuner).setCH("6");
        }

        @Test
        @DisplayName("N-06: Given 456 입력 후 — When 채널업 — Then 45·46, 미확인 6 무효")
        void n06_pendingDigitClearedOnChannelUp() {
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
        @DisplayName("N-07: Given 초기 — When 0·7 — Then 7번 (선행 0)")
        void n07_leadingZeroIgnored() {
            controller.pushButton(remoteKey.KEY_0);
            controller.pushButton(remoteKey.KEY_7);

            verify(tuner).setCH("7");
        }

        @Test
        @DisplayName("OK-02: Given 빈 버퍼 — When 확인 — Then setCH 호출 없음")
        void ok02_emptyBufferOkIgnored() {
            controller.pushButton(remoteKey.KEY_OK);

            verify(tuner, never()).setCH(anyString());
        }

        @ParameterizedTest(name = "B-KEY: digit={0} + OK → ch={0}")
        @CsvSource({"0", "1", "9"})
        @DisplayName("B-KEY-01/03/09: Given 단일 숫자 — When 확인 — Then 해당 채널 (경계 0·1·9)")
        void boundarySingleDigitWithOk(int digit) {
            pushDigit(digit);
            controller.pushButton(remoteKey.KEY_OK);

            verify(tuner).setCH(String.valueOf(digit));
        }

        @ParameterizedTest(name = "B-CH: {0}+{1} → {2}")
        @CsvSource({
                "9, 8, 98",
                "9, 9, 99"
        })
        @DisplayName("B-CH-03/04: Given 연속 2자리 — When 두 번째 숫자 — Then 98·99 (경계)")
        void boundaryTwoDigitChannels(int first, int second, int expected) {
            pushDigit(first);
            pushDigit(second);

            verify(tuner).setCH(String.valueOf(expected));
        }

        @Test
        @DisplayName("B-KEY-05: Given 99 확정 — When 확인 — Then setCH 1회만")
        void boundary99OkDoesNotRetune() {
            controller.pushButton(remoteKey.KEY_9);
            controller.pushButton(remoteKey.KEY_9);
            controller.pushButton(remoteKey.KEY_OK);

            verify(tuner).setCH("99");
            verify(tuner, times(1)).setCH(anyString());
        }

        @Test
        @DisplayName("B-INV-03: Given 99 확정 후 — When 9·확인 — Then 99 후 9번")
        void boundary99ThenSingleNineWithOk() {
            controller.pushButton(remoteKey.KEY_9);
            controller.pushButton(remoteKey.KEY_9);
            pushDigit(9);
            controller.pushButton(remoteKey.KEY_OK);

            InOrder inOrder = inOrder(tuner);
            inOrder.verify(tuner).setCH("99");
            inOrder.verify(tuner).setCH("9");
        }
    }

    @Nested
    @DisplayName("2. 채널 업/다운 (검색 없음)")
    class ChannelUpDownWithoutScan {

        @ParameterizedTest(name = "UD-UP: ch={0} → {1}")
        @CsvSource({
                "6, 7",
                "99, 0",
                "1, 2",
                "98, 99"
        })
        @DisplayName("UD-01/03: Given 검색 없음·현재 ch — When 업 — Then 경계 포함 ±1·순환")
        void channelUpWithoutScan(String current, String expected) {
            stubChannelTracking();
            currentChannel.set(current);

            controller.pushButton(remoteKey.KEY_CH_UP);

            verify(tuner).setCH(expected);
        }

        @ParameterizedTest(name = "UD-DOWN: ch={0} → {1}")
        @CsvSource({
                "6, 5",
                "0, 99",
                "1, 0",
                "98, 97"
        })
        @DisplayName("UD-02/04: Given 검색 없음·현재 ch — When 다운 — Then 경계 포함 ±1·순환")
        void channelDownWithoutScan(String current, String expected) {
            stubChannelTracking();
            currentChannel.set(current);

            controller.pushButton(remoteKey.KEY_CH_DOWN);

            verify(tuner).setCH(expected);
        }

        @Test
        @DisplayName("B-CH-05: Given ch=99·검색 없음 — When 업 — Then 0번 (상한 순환)")
        void boundary99WrapsToZeroOnUp() {
            stubChannelTracking();
            currentChannel.set("99");

            controller.pushButton(remoteKey.KEY_CH_UP);

            verify(tuner).setCH("0");
        }

        @Test
        @DisplayName("B-CH-06: Given ch=0·검색 없음 — When 다운 — Then 99번 (하한 순환)")
        void boundary0WrapsTo99OnDown() {
            stubChannelTracking();
            currentChannel.set("0");

            controller.pushButton(remoteKey.KEY_CH_DOWN);

            verify(tuner).setCH("99");
        }

        @Test
        @DisplayName("B-INV-04: Given 숫자 버퍼 대기 — When 다운 — Then 버퍼 클리어 후 ch-1")
        void boundaryBufferClearedOnDownDuringDigits() {
            stubChannelTracking();
            currentChannel.set("10");

            controller.pushButton(remoteKey.KEY_1);
            controller.pushButton(remoteKey.KEY_CH_DOWN);

            verify(tuner).setCH("9");
            verify(tuner, never()).setCH("1");
        }
    }

    @Nested
    @DisplayName("3. 채널 검색")
    class ChannelSearch {

        @Test
        @DisplayName("S-01: Given seekCH 3회 반환 — When 검색 — Then seekCH 3회 호출")
        void s01_searchInvokesSeekUntilLoop() {
            when(tuner.getCurrentCH()).thenReturn("6");
            when(tuner.seekCH()).thenReturn("14", "4", "6");

            controller.pushButton(remoteKey.KEY_SEARCH);

            verify(tuner, times(3)).seekCH();
        }

        @Test
        @DisplayName("X-05: Given seekCH 즉시 순환 — When 검색 — Then seekCH 1회")
        void x05_searchStopsOnImmediateCycle() {
            when(tuner.getCurrentCH()).thenReturn("7");
            when(tuner.seekCH()).thenReturn("7");

            controller.pushButton(remoteKey.KEY_SEARCH);

            verify(tuner, times(1)).seekCH();
        }

        @Test
        @DisplayName("X-02: Given 숫자 버퍼 4 대기 — When 검색 — Then setCH 없음·버퍼 클리어")
        void x02_searchClearsDigitBuffer() {
            when(tuner.getCurrentCH()).thenReturn("6");
            when(tuner.seekCH()).thenReturn("14", "4", "6");

            controller.pushButton(remoteKey.KEY_4);
            controller.pushButton(remoteKey.KEY_SEARCH);
            controller.pushButton(remoteKey.KEY_OK);

            verify(tuner, never()).setCH(anyString());
        }

        @Test
        @DisplayName("X-03: Given 검색 완료·ch=6 — When 업 — Then ±1 아닌 목록 기준 14")
        void x03_afterSearchUpUsesListNotLinear() {
            stubChannelTracking();
            currentChannel.set("6");
            when(tuner.seekCH()).thenReturn("14", "4", "6");

            controller.pushButton(remoteKey.KEY_SEARCH);
            controller.pushButton(remoteKey.KEY_CH_UP);

            verify(tuner).setCH("14");
            verify(tuner, never()).setCH("7");
        }

        @Test
        @DisplayName("S-02: Given 검색 목록 — When 재검색 — Then seekCH 전체 스캔 재수행")
        void s02_reSearchInvokesSeekAgain() {
            when(tuner.getCurrentCH()).thenReturn("6");
            when(tuner.seekCH()).thenReturn("14", "4", "6", "14", "4", "6");

            controller.pushButton(remoteKey.KEY_SEARCH);
            controller.pushButton(remoteKey.KEY_SEARCH);

            verify(tuner, times(6)).seekCH();
        }
    }

    @Nested
    @DisplayName("4. 채널 업/다운 (검색 목록 기준)")
    class ChannelUpDownWithScan {

        private void scanChannels4_6_14() {
            when(tuner.seekCH()).thenReturn("14", "4", "6");
            controller.pushButton(remoteKey.KEY_SEARCH);
        }

        @Test
        @DisplayName("UD-05: Given 목록 4·6·14·ch=6 — When 업 — Then 14")
        void ud05_upFromChannelInList() {
            stubChannelTracking();
            currentChannel.set("6");
            scanChannels4_6_14();

            controller.pushButton(remoteKey.KEY_CH_UP);

            verify(tuner).setCH("14");
        }

        @Test
        @DisplayName("UD-06: Given 목록 4·6·14·ch=6 — When 다운 — Then 4")
        void ud06_downFromChannelInList() {
            stubChannelTracking();
            currentChannel.set("6");
            scanChannels4_6_14();

            controller.pushButton(remoteKey.KEY_CH_DOWN);

            verify(tuner).setCH("4");
        }

        @Test
        @DisplayName("UD-07: Given 목록 4·6·14·ch=15 — When 업 — Then 4 (목록 순환)")
        void ud07_upFromOutsideList() {
            stubChannelTracking();
            currentChannel.set("15");
            scanChannels4_6_14();

            controller.pushButton(remoteKey.KEY_CH_UP);

            verify(tuner).setCH("4");
        }

        @Test
        @DisplayName("UD-08: Given 목록 4·6·14·ch=15 — When 다운 — Then 14")
        void ud08_downFromOutsideList() {
            stubChannelTracking();
            currentChannel.set("15");
            scanChannels4_6_14();

            controller.pushButton(remoteKey.KEY_CH_DOWN);

            verify(tuner).setCH("14");
        }

        @Test
        @DisplayName("B-CH-07a: Given 검색 목록·ch=98 — When 업 — Then 99")
        void boundaryScannedList98UpTo99() {
            stubChannelTracking();
            currentChannel.set("98");
            when(tuner.seekCH()).thenReturn("99", "98");

            controller.pushButton(remoteKey.KEY_SEARCH);
            controller.pushButton(remoteKey.KEY_CH_UP);

            verify(tuner).setCH("99");
        }

        @Test
        @DisplayName("B-CH-07b: Given 검색 목록·ch=99 — When 업 — Then 98 (목록 순환)")
        void boundaryScannedList99UpWrapsTo98() {
            stubChannelTracking();
            currentChannel.set("99");
            when(tuner.seekCH()).thenReturn("98", "99");

            controller.pushButton(remoteKey.KEY_SEARCH);
            controller.pushButton(remoteKey.KEY_CH_UP);

            verify(tuner).setCH("98");
        }
    }

    @Nested
    @DisplayName("5. 선호 채널")
    class FavoriteChannel {

        @Test
        @DisplayName("P-01: Given 선호 1·4·12·56·ch=6 — When 다음선호 — Then 12")
        void p01_nextFavoriteSkipsToSmallestGreater() {
            stubChannelTracking();
            addFavorites(1, 4, 12, 56);
            currentChannel.set("6");

            controller.pushButton(remoteKey.KEY_FAV_NEXT);

            verify(tuner).setCH("12");
        }

        @Test
        @DisplayName("P-02: Given 선호·ch=56 — When 다음선호 — Then 1 (로테이션)")
        void p02_nextFavoriteRotatesFromMax() {
            stubChannelTracking();
            addFavorites(1, 4, 12, 56);
            currentChannel.set("56");

            controller.pushButton(remoteKey.KEY_FAV_NEXT);

            verify(tuner).setCH("1");
        }

        @Test
        @DisplayName("F-01: Given ch=6 비선호 — When 선호추가·다음선호 — Then 6번")
        void f01_addFavoriteThenNextGoesToAdded() {
            stubChannelTracking();
            currentChannel.set("6");
            controller.pushButton(remoteKey.KEY_FAV_ADD);
            currentChannel.set("5");

            controller.pushButton(remoteKey.KEY_FAV_NEXT);

            verify(tuner).setCH("6");
        }

        @Test
        @DisplayName("F-02: Given ch=6 선호 — When 선호추가 2회·다음선호 — Then 12 (6 제외)")
        void f02_toggleOffFavoriteExcludesFromNext() {
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
        @DisplayName("P-03: Given 선호 1개·ch=7 — When 다음선호 — Then 7 (동일 로테이션)")
        void p03_singleFavoriteRotatesToSelf() {
            stubChannelTracking();
            currentChannel.set("7");
            controller.pushButton(remoteKey.KEY_FAV_ADD);

            controller.pushButton(remoteKey.KEY_FAV_NEXT);

            verify(tuner).setCH("7");
        }

        @Test
        @DisplayName("P-04: Given 선호 없음 — When 다음선호 — Then setCH 없음")
        void p04_emptyFavoritesNextIgnored() {
            when(tuner.getCurrentCH()).thenReturn("6");

            controller.pushButton(remoteKey.KEY_FAV_NEXT);

            verify(tuner, never()).setCH(anyString());
        }

        @Test
        @DisplayName("B-CH-08: Given 선호 0·99·ch=50 — When 다음선호 — Then 99")
        void boundaryFavorites0And99() {
            stubChannelTracking();
            addFavorites(0, 99);
            currentChannel.set("50");

            controller.pushButton(remoteKey.KEY_FAV_NEXT);

            verify(tuner).setCH("99");
        }

        @Test
        @DisplayName("X-01: Given 숫자 버퍼 대기 — When 선호추가 — Then 버퍼 클리어·OK 무효")
        void x01_favoriteAddClearsDigitBuffer() {
            when(tuner.getCurrentCH()).thenReturn("45");

            controller.pushButton(remoteKey.KEY_4);
            controller.pushButton(remoteKey.KEY_FAV_ADD);
            controller.pushButton(remoteKey.KEY_OK);

            verify(tuner, never()).setCH(anyString());
        }
    }
}
