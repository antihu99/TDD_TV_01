# TDD_TV_01 — User Prompts Only

**Export**: 2026-05-19  
**Sessions**: `3b7d5ee7-...` (7) + `17ca3870-...` (3) + **Session 3** (5)  
**Total**: 15 prompts

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
