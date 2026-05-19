package com.bestreviewer;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * (선택) ApprovalTests 기반 Golden Master.
 * <p>
 * 기준 파일: {@code TVControllerGoldenMasterApprovalsTest.masterTrace.approved.txt}
 * (테스트 클래스와 동일 패키지·이름 규칙)
 * <p>
 * 갱신: {@code mvn test -Dtest=TVControllerGoldenMasterApprovalsTest -Dapprovaltests.useForWindows=true}
 * 또는 IDE에서 Approvals 실패 시 생성된 {@code .received.txt}를 {@code .approved.txt}로 승인.
 */
@DisplayName("TVController — Golden Master (ApprovalTests)")
class TVControllerGoldenMasterApprovalsTest {

    @Test
    @DisplayName("GM-A01: ApprovalTests로 마스터 트레이스 승인")
    void masterTraceApproved() {
        String actual = TVControllerGoldenMasterScenarios.buildMasterTrace();
        Approvals.verify(actual);
    }
}
