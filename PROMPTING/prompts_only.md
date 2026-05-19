# TDD_TV_01 — User Prompts Only

**Export**: 2026-05-19  
**Sessions**: `3b7d5ee7-...` (7) + `17ca3870-...` (3) + **Session 3** (5) + **Session 4** (3) + **Session 5** (3) + **Session 6** (4)  
**Total**: 25 prompts

---

## Session 1 — `3b7d5ee7-d341-4044-811c-f29bda2a0a36`

### Prompt 1

```
teminal prompt 의 대화내용을 github_setting.txt 파일로 만들어줘
```

**산출물**: `github_setting.txt` (터미널 Git 작업 기록)

---

### Prompt 2

```
명령어 부분만 보완해서 파일을 만들어줘
```

**산출물**: `github_setting.txt` (명령어 중심 버전)

---

### Prompt 3

```
첫번째 제시했던 내용도 모두 포함해서 만들어줘
```

**산출물**: `github_setting.txt` (상세 내용 + 명령어 통합)

---

### Prompt 4 (P/C/T/F)

```
[P] 당신은 레거시 코드 QA/리팩토링을 돕는 시니어 Java 엔지니어입니다.
[C] TDD_TV Java 프로젝트에서 Cursor AI가 항상 따라야 하는 규칙을
    프로젝트 루트의 .cursorrules로 작성하려고 합니다.
[T] 아래 요구를 만족하는 .cursorrules 내용을 작성해줘.
    - 기술 스택: Java 21 + Maven + JUnit 5 (+ JaCoCo)
    - 절대 규칙: Item 클래스 수정 금지, 입력 숫자( 0,1,2,3,4,5,6,7,8,9 ) 채널범위 0~99
    - 테스트 규칙: Given-When-Then, @ParameterizedTest, 키 값은 입력으로 들어올 것
    - 리팩토링 규칙: 테스트 Green 상태에서만 진행, 매직 넘버 상수화 권장
[F] 위 내용으로 .cursorrules 파일을 만들어줘
```

**산출물**: `.cursorrules`

---

### Prompt 5

```
REPORT 폴더에 보고서를 작성해줘
```

**산출물**: `REPORT/00.TDD_TV_01_작업보고서.md` (초기 `TDD_TV_01_작업보고서.md`)

---

### Prompt 6

```
PDF용 요약본만 추가적으로 만들어줘
```

**산출물**: `REPORT/TDD_TV_01_요약본_PDF용.md`

---

### Prompt 7

```
현재까지의 prompt 를 prompting 폴더에 export transcript 해줘
```

**산출물**: `prompting/` (1차 export — `prompting_base` 참고)

---

## Session 2 — `17ca3870-850a-4f1d-820c-be3e6882786f`

### Prompt 8 (P/C/T/F)

```
@TddTvRequirements.txt @README.md

[P] 시니어 Java QA 엔지니어 관점에서
[C] Gilded Rose Java 프로젝트 (Java 21, Maven, JUnit 5)
[T] 요구사항을 분석해서 아래를 정리해줘.
    0) 기능별 비즈니스 규칙 표
      - 채널 업/다운, 채널 확인, 채널검색, 선호채널추가, 다음선호채널 버튼
    1) 기본적인 메소드 목록
       - seekCH() / setCH() / getCurrentCH()
    2) 예외/경계값 조건 (입력0,1,2,3,4,5,6,7,8,9 , 채널 0~ 99 )
    3) readme 파일을 읽고, controller 모듈의 동작 기능을 나열하고, 설명
    4) 테스트해야 할 주요 시나리오 목록 (번호)
[F] Markdown (표 + 번호 목록). docs/requirements_analysis.md로 저장해줘
```

**산출물**: `docs/requirements_analysis.md`  
**참고**: `TddTvRequirements.txt` 없음 → `README.md` 기준, TDD_TV 도메인으로 분석

---

### Prompt 9

```
report 폴더에 보고서를 작성해줘
```

**산출물**: `REPORT/01.TDD_TV_01_요구사항분석_보고서.md`, `00`·PDF 요약본 갱신

---

### Prompt 10

```
현재까지의 prompt 를 prompting 폴더에 export transcript 해줘
```

**산출물**: `prompting/*` (본 export — 2세션 통합)

---

## Session 3 — 테스트 계획·JaCoCo (2026-05-19)

### Prompt 11 (P/C/T/F)

```
@TVController.java @TddTvRequirements.txt @requirements_analysis.md

[P] 시니어 QA 리드입니다.
[C] Java 21, JUnit 5, Maven, (가능하면 JaCoCo)
[T] 테스트 계획서를 작성해줘.
    - 단위 테스트 범위/우선순위
    - 경계값 테스트 (key 0,1,98,99 )
    - 예외/특이 케이스 목록
    - 커버리지 목표 (예: 90%+)와 JaCoCo 달성 전략
[F] Markdown 문서로 docs/03.test_plan.md로 저장
```

**산출물**: `docs/03.test_plan.md`

---

### Prompt 12

```
jacoco 플러그인을 pom.xml 에 적용하고, 갭 test 를 바로 구현해줘
```

**산출물**: `pom.xml`(JaCoCo), `TVControllerTest` 보강, `ChannelInputBufferTest`, `FavoriteChannelsTest`, `ChannelConstantsTest` — 53건 Green, `mvn verify` 통과

---

### Prompt 13

```
report 폴더에 보고서를 작성해줘
```

**산출물**: `REPORT/03.TDD_TV_01_테스트계획_구현_보고서.md`

---

### Prompt 14

```
네
```

**산출물**: `REPORT/00.TDD_TV_01_작업보고서.md` — 03 링크·테스트/JaCoCo 현황 갱신

---

### Prompt 15

```
prompting 폴더에 agent 와의 대화를 저장해줘
```

**산출물**: `PROMPTING/03.TDD_TV_01_테스트계획_구현_보고서_prompting.md`, `prompts_only.md`, `README.md` 갱신

---

## Session 4 — TVControllerTest 설계·보강 (2026-05-19)

### Prompt 16 (P/C/T/F)

```
@TVControllerTest.java @TVController.java @TddTvRequirements.txt @requirements_analysis.md @03.test_plan.md

[P] 테스트 설계에 강한 시니어 Java QA입니다.
[C] Java 21, JUnit 5
[T] 기능별 최소 5개 테스트를 작성해줘.
    - @DisplayName + Given-When-Then
    - 경계값 반드시 포함
    - 가능한 경우 @ParameterizedTest + @CsvSource 사용
[F] 완성된 테스트 코드 (파일 단위 수정 포함). mvn test가 Green이 되게 작성
```

**산출물**: `TVControllerTest.java` — `@Nested` 5영역, 45건, `mvn test` 63건 Green

---

### Prompt 17

```
report 폴더에 보고서를 작성해줘
```

**산출물**: `REPORT/04.TDD_TV_01_TVControllerTest_설계_보고서.md`, `REPORT/TDD_TV_01_요약본_PDF용.md` 갱신

---

### Prompt 18

```
prompting 폴더에 agent 와의 대화를 저장해줘
```

**산출물**: `PROMPTING/04.TDD_TV_01_TVControllerTest_설계_보고서_prompting.md`, `prompts_only.md`, `README.md` 갱신

---

## Session 5 — TVController 결함 분석 (2026-05-19)

### Prompt 19 (P/C/T/F)

```
@TVControllerTest.java @TVController.java

[P] 디버깅과 결함 분석에 능한 Java QA 엔지니어입니다.
[T]TVControllerTest.java 실패 로그가 있을까요? 있다면 
    1) 실패 원인 (기대/실제 값 차이) 요약
    2) TVController()에서 버그 위치 특정 (파일명:줄번호)
    3) 결함 심각도 (Critical/Major/Minor/Info) 분류 및 근거
    4) 최소 코드 변경으로 수정 방안 제안
       - 단, Item 클래스는 수정 금지
[F] 수정 diff 제안 + 수정 후 mvn test Green 확인 절차
```

**산출물**: Surefire 조사 — Failures 0, `TVController` 결함 없음, 과거 S-02·B-CH-07 스텁·레거시 Critical 분석

---

### Prompt 20

```
report 폴더에 보고서를 작성해줘
```

**산출물**: `REPORT/05.TDD_TV_01_TVController_결함분석_보고서.md`, `REPORT/TDD_TV_01_요약본_PDF용.md` 갱신

---

### Prompt 21

```
네... PROMPTING 폴더에 agent 와의 대화 내용을 저장해 주세요
```

**산출물**: `PROMPTING/05.TDD_TV_01_TVController_결함분석_보고서_prompting.md`, `prompts_only.md`, `README.md` 갱신

---

## Session 6 — Golden Master 회귀 (2026-05-19)

### Prompt 22 (P/C/T/F)

```
@TVControllerTest.java

[P] 회귀 테스트(Approval/Golden Master) 설계 전문가입니다.
[C] Java 21, JUnit 5, Maven
[T] Golden Master를 설계/구현해줘.
    1) 기준 출력(golden_master_expected.txt)을 생성/보관하는 방법
    2) 테스트에서 actual 출력과 파일 비교하는 방법
    3) CI에서 mvn test로 자동 실행되는 구성
    4) (선택) Approvals 테스트 적용 가능성
[F] 테스트 코드 + 파일 저장/비교 구현 + 실행 방법
```

**산출물**: `RecordingTuner`, `TVControllerGoldenMasterScenarios`, `GoldenMasterSupport`, `TVControllerGoldenMasterTest`, `golden_master_expected.txt`, `.github/workflows/ci.yml`, (선택) `TVControllerGoldenMasterApprovalsTest` — `mvn test` 66건 Green

---

### Prompt 23

```
Golden Master 절을 docs 폴더에 04.Golden_Master 파일로 저장하고, 더불어 docs/03.test_plan.md 파일 Golden Master 절을 넣어 docs/03.test_plan_new로 만들어줘
```

**산출물**: `docs/04.Golden_Master.md`, `docs/03.test_plan_new.md` (§4 Golden Master, §5~§11 재번호)

---

### Prompt 24

```
report 폴더에 보고서를 작성해줘
```

**산출물**: `REPORT/06.TDD_TV_01_Golden_Master_구현_보고서.md`, `REPORT/00` §4.7·산출물 목록 갱신

---

### Prompt 25

```
PROMPTING 폴더에 AGENT 와의 대화를 저장해줘
```

**산출물**: `PROMPTING/06.TDD_TV_01_Golden_Master_구현_보고서_prompting.md`, `prompts_only.md`, `README.md` 갱신

---

## 세션 산출물 요약

| # | Prompt 요약 | 생성/수정 파일 |
|---|-------------|----------------|
| 1 | 터미널 → github_setting | `github_setting.txt` |
| 2 | 명령어만 보완 | `github_setting.txt` |
| 3 | 상세+명령 통합 | `github_setting.txt` |
| 4 | .cursorrules (PCTF) | `.cursorrules` |
| 5 | REPORT 보고서 | `REPORT/00.TDD_TV_01_작업보고서.md` |
| 6 | PDF용 요약 | `REPORT/TDD_TV_01_요약본_PDF용.md` |
| 7 | transcript export (1차) | `prompting_base/` |
| 8 | 요구사항 QA 분석 (PCTF) | `docs/requirements_analysis.md` |
| 9 | 요구사항 분석 보고서 | `REPORT/01.TDD_TV_01_요구사항분석_보고서.md` |
| 10 | transcript export (2차) | `prompting/*` |
| 11 | 테스트 계획서 (PCTF) | `docs/03.test_plan.md` |
| 12 | JaCoCo + 갭 테스트 | `pom.xml`, 테스트 53건 |
| 13 | 테스트 REPORT | `REPORT/03` |
| 14 | 00 보고서 링크 | `REPORT/00` 갱신 |
| 15 | transcript export (3차) | `PROMPTING/03_*_prompting.md` |
| 16 | TVControllerTest 설계 (PCTF) | `TVControllerTest` 45건 `@Nested` |
| 17 | TVControllerTest REPORT | `REPORT/04` |
| 18 | transcript export (4차) | `PROMPTING/04_*_prompting.md` |
| 19 | TVController 결함 분석 (PCTF) | 분석만 (코드 변경 없음) |
| 20 | 결함 분석 REPORT | `REPORT/05` |
| 21 | transcript export (5차) | `PROMPTING/05_*_prompting.md` |
| 22 | Golden Master 설계·구현 (PCTF) | Golden Master 테스트·baseline·CI |
| 23 | docs/04 + test_plan_new | `docs/04.Golden_Master.md`, `docs/03.test_plan_new.md` |
| 24 | Golden Master REPORT | `REPORT/06` |
| 25 | transcript export (6차) | `PROMPTING/06_*_prompting.md` |
