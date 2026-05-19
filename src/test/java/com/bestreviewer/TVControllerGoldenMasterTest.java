package com.bestreviewer;

import com.bestreviewer.golden.GoldenMasterSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Golden Master 회귀 테스트: TVController 통합 동작 트레이스를
 * {@code src/test/resources/golden_master/golden_master_expected.txt}와 비교한다.
 */
@DisplayName("TVController — Golden Master 회귀")
class TVControllerGoldenMasterTest {

    @Test
    @DisplayName("GM-01: 마스터 시나리오 트레이스가 golden_master_expected.txt와 일치")
    void masterTraceMatchesGoldenFile() throws IOException {
        String actual = TVControllerGoldenMasterScenarios.buildMasterTrace();

        if (GoldenMasterSupport.isUpdateMode()) {
            GoldenMasterSupport.writeExpectedToRepo(actual);
            return;
        }

        String expected = GoldenMasterSupport.loadExpectedFromClasspath();
        GoldenMasterSupport.assertMatchesGolden(actual, expected);
    }

    @Test
    @DisplayName("GM-02: 기준 파일이 비어 있지 않음 (CI 누락 방지)")
    void goldenFileIsPresent() throws IOException {
        assertFalse(GoldenMasterSupport.loadExpectedFromClasspath().isBlank());
    }
}
