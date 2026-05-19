package com.bestreviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * TVControllerTest 핵심 시나리오를 하나의 결정적 트레이스로 직렬화한다.
 */
public final class TVControllerGoldenMasterScenarios {

    private TVControllerGoldenMasterScenarios() {
    }

    public static String buildMasterTrace() {
        List<String> sections = new ArrayList<>();
        sections.add(section("N-01", "1·확인 → 1번", t -> runKeys(t, remoteKey.KEY_1, remoteKey.KEY_OK)));
        sections.add(section("N-02", "1·2 → 12번", t -> runKeys(t, remoteKey.KEY_1, remoteKey.KEY_2)));
        sections.add(section("N-03", "1·2·3·4 → 12 후 34", t ->
                runKeys(t, remoteKey.KEY_1, remoteKey.KEY_2, remoteKey.KEY_3, remoteKey.KEY_4)));
        sections.add(section("N-04", "4·5·6 → 45번", t ->
                runKeys(t, remoteKey.KEY_4, remoteKey.KEY_5, remoteKey.KEY_6)));
        sections.add(section("N-05", "456·확인 → 45 후 6", t ->
                runKeys(t, remoteKey.KEY_4, remoteKey.KEY_5, remoteKey.KEY_6, remoteKey.KEY_OK)));
        sections.add(section("N-06", "456·채널업 → 45·46 (6 무효)", t ->
                runKeys(t, remoteKey.KEY_4, remoteKey.KEY_5, remoteKey.KEY_6, remoteKey.KEY_CH_UP)));
        sections.add(section("N-07", "0·7 → 7번", t -> runKeys(t, remoteKey.KEY_0, remoteKey.KEY_7)));
        sections.add(section("OK-02", "빈 버퍼·확인 → setCH 없음", t -> runKeys(t, remoteKey.KEY_OK)));
        sections.add(section("UD-01", "ch=6·업 → 7", t -> {
            t.prepareChannel("6");
            runKeys(t, remoteKey.KEY_CH_UP);
        }));
        sections.add(section("UD-03", "ch=99·업 → 0", t -> {
            t.prepareChannel("99");
            runKeys(t, remoteKey.KEY_CH_UP);
        }));
        sections.add(section("UD-04", "ch=0·다운 → 99", t -> {
            t.prepareChannel("0");
            runKeys(t, remoteKey.KEY_CH_DOWN);
        }));
        sections.add(section("S-01", "검색 seek 3회", t -> {
            t.prepareChannel("6");
            t.enqueueSeekResponses("14", "4", "6");
            runKeys(t, remoteKey.KEY_SEARCH);
        }));
        sections.add(section("X-05", "검색 즉시 순환 seek 1회", t -> {
            t.prepareChannel("7");
            t.enqueueSeekResponses("7");
            runKeys(t, remoteKey.KEY_SEARCH);
        }));
        sections.add(section("X-02", "버퍼 4·검색·확인 → setCH 없음", t -> {
            t.prepareChannel("6");
            t.enqueueSeekResponses("14", "4", "6");
            runKeys(t, remoteKey.KEY_4, remoteKey.KEY_SEARCH, remoteKey.KEY_OK);
        }));
        sections.add(section("UD-05", "검색목록·ch=6·업 → 14", t -> {
            t.prepareChannel("6");
            t.enqueueSeekResponses("14", "4", "6");
            runKeys(t, remoteKey.KEY_SEARCH, remoteKey.KEY_CH_UP);
        }));
        sections.add(section("P-01", "선호 1·4·12·56·ch=6·다음선호 → 12", t -> {
            TVController controller = newController(t);
            addFavorites(controller, t, 1, 4, 12, 56);
            t.prepareChannel("6");
            controller.pushButton(remoteKey.KEY_FAV_NEXT);
        }));
        sections.add(section("P-04", "선호 없음·다음선호 → setCH 없음", t -> {
            t.prepareChannel("6");
            runKeys(t, remoteKey.KEY_FAV_NEXT);
        }));
        sections.add(section("F-02", "선호 토글 제외·다음선호 → 12", t -> {
            TVController controller = newController(t);
            addFavorites(controller, t, 1, 4, 12, 56);
            t.prepareChannel("6");
            controller.pushButton(remoteKey.KEY_FAV_ADD);
            controller.pushButton(remoteKey.KEY_FAV_ADD);
            t.prepareChannel("5");
            controller.pushButton(remoteKey.KEY_FAV_NEXT);
        }));
        return String.join("\n", sections) + "\n";
    }

    private static String section(String id, String description, Consumer<RecordingTuner> scenario) {
        RecordingTuner tuner = new RecordingTuner();
        tuner.setInitialChannel("0");
        scenario.accept(tuner);
        tuner.appendFinalChannel();
        return formatSection(id, description, tuner.getTrace());
    }

    private static String formatSection(String id, String description, List<String> trace) {
        StringBuilder sb = new StringBuilder();
        sb.append("### ").append(id).append(": ").append(description).append('\n');
        for (String line : trace) {
            sb.append(line).append('\n');
        }
        return sb.toString().trim();
    }

    private static TVController newController(RecordingTuner tuner) {
        return new TVController(tuner);
    }

    private static void runKeys(RecordingTuner tuner, remoteKey... keys) {
        TVController controller = newController(tuner);
        for (remoteKey key : keys) {
            controller.pushButton(key);
        }
    }

    private static void addFavorites(RecordingTuner tuner, int... channels) {
        TVController controller = newController(tuner);
        addFavorites(controller, tuner, channels);
    }

    private static void addFavorites(TVController controller, RecordingTuner tuner, int... channels) {
        for (int channel : channels) {
            tuner.prepareChannel(String.valueOf(channel));
            controller.pushButton(remoteKey.KEY_FAV_ADD);
        }
    }
}
