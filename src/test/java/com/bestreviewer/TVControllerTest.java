// package com.bestreviewer;

// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;

// import static org.mockito.Mockito.mock;

// class TVControllerTest {

//     TVController tvCtrl = new TVController(mock(Tuner.class));

//     @Test
//     void testFramework() {
//         // fail();

//         tvCtrl.setTunerCh();
//     }
// }
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TvControllerTest {

    private TvController controller;

    private FakeTuner tuner;

    @BeforeEach
    void setup() {

        tuner = new FakeTuner(
                Arrays.asList(4, 6, 14, 22, 56));

        controller = new TvController(tuner);
    }

    @Test
    @DisplayName("숫자_1_확인_채널1로변경")
    void Key1Test() {

        controller.press(RemoteKey.NUM_1);
        controller.press(RemoteKey.OK);

        assertEquals(1, tuner.getCurrentCH());
    }

    @Test
    @DisplayName("숫자_1_2_입력시_12채널로변경")
    void Key12Test() {

        controller.press(RemoteKey.NUM_1);
        controller.press(RemoteKey.NUM_2);

        assertEquals(12, tuner.getCurrentCH());
    }

    @Test
    @DisplayName("숫자_1_2_3_4_입력시_12후34변경")
    void Key1234Test() {

        
        controller.press(RemoteKey.NUM_1);
        controller.press(RemoteKey.NUM_2);

        assertEquals(12, tuner.getCurrentCH());

        controller.press(RemoteKey.NUM_3);
        controller.press(RemoteKey.NUM_4);

        assertEquals(34, tuner.getCurrentCH());
    }

    @Test
    @DisplayName("숫자_0_7은_7채널")
    void Key07Test() {

        controller.press(RemoteKey.NUM_0);
        controller.press(RemoteKey.NUM_7);

        assertEquals(7, tuner.getCurrentCH());
    }

    @Test
    @DisplayName("선호채널_추가_삭제")
    void FavoriteAddRemoveTest() {

        tuner.setCH(12);

        controller.press(RemoteKey.FAVORITE_ADD);

        assertTrue(controller.getFavorites().contains(12));

        controller.press(RemoteKey.FAVORITE_ADD);

        assertEquals(0, controller.getFavorites().size());
    }

    @Test
    @DisplayName("다음선호채널_이동")
    void NextFavoriteTest() {

        tuner.setCH(1);
        controller.press(RemoteKey.FAVORITE_ADD);

        tuner.setCH(4);
        controller.press(RemoteKey.FAVORITE_ADD);

        tuner.setCH(12);
        controller.press(RemoteKey.FAVORITE_ADD);

        tuner.setCH(56);
        controller.press(RemoteKey.FAVORITE_ADD);

        tuner.setCH(6);

        controller.press(RemoteKey.NEXT_FAVORITE);

        assertEquals(12, tuner.getCurrentCH());
    }

    @Test
    @DisplayName("업다운 정상 작동")
    void NormalUpDownTest() {

        tuner.setCH(6);

        controller.press(RemoteKey.CHANNEL_UP);
        assertEquals(7, tuner.getCurrentCH());

        controller.press(RemoteKey.CHANNEL_DOWN);
        assertEquals(6, tuner.getCurrentCH());
    }

    @Test
    @DisplayName("업다운 로테이션")
    void UpDownR() {

        tuner.setCH(99);

        controller.press(RemoteKey.CHANNEL_UP);

        assertEquals(0, tuner.getCurrentCH());

        controller.press(RemoteKey.CHANNEL_DOWN);

        assertEquals(99, tuner.getCurrentCH());
    }

    @Test
    @DisplayName("검색된 채널 기준 업다운")
    void SearchedChannelUpDownTest() {

        controller.searchChannels();

        tuner.setCH(6);

        controller.press(RemoteKey.CHANNEL_UP);

        assertEquals(14, tuner.getCurrentCH());

        controller.press(RemoteKey.CHANNEL_DOWN);

        assertEquals(6, tuner.getCurrentCH());
    }
}