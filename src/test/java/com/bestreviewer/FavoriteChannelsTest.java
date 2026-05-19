package com.bestreviewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FavoriteChannelsTest {

    private FavoriteChannels favorites;

    @BeforeEach
    void setUp() {
        favorites = new FavoriteChannels();
    }

    @Test
    @DisplayName("F-01: toggle로 선호 채널 추가")
    void toggleAddsFavorite() {
        favorites.toggle(6);

        assertEquals(Optional.of(6), favorites.nextAfter(5));
    }

    @Test
    @DisplayName("F-02: toggle 두 번으로 선호 채널 제거")
    void toggleRemovesFavorite() {
        favorites.toggle(6);
        favorites.toggle(6);

        assertFalse(favorites.nextAfter(5).isPresent());
    }

    @Test
    @DisplayName("P-03: 선호 1개일 때 nextAfter는 로테이션")
    void nextAfterRotatesWithSingleFavorite() {
        favorites.toggle(7);

        assertEquals(Optional.of(7), favorites.nextAfter(7));
    }

    @Test
    @DisplayName("P-04: 선호 없을 때 nextAfter는 empty")
    void nextAfterEmptyWhenNoFavorites() {
        assertFalse(favorites.nextAfter(6).isPresent());
    }

    @Test
    @DisplayName("P-01: 현재보다 큰 최소 선호 채널 반환")
    void nextAfterReturnsSmallestGreaterThanCurrent() {
        favorites.toggle(1);
        favorites.toggle(4);
        favorites.toggle(12);
        favorites.toggle(56);

        assertEquals(Optional.of(12), favorites.nextAfter(6));
    }
}
