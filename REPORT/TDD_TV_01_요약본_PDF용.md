# TDD_TV_01 작업 요약 (PDF용)

**프로젝트** TDD_TV Maven · **작성자** 김경림 · **작성일** 2026-05-19  
**저장소** https://github.com/antihu99/TDD_TV_01.git

> **PDF 저장 방법**: 본 파일을 연 뒤 `Ctrl+P` → 대상 **PDF로 저장** (또는 Markdown PDF 확장 사용).

---

## 1. 한 줄 요약

리모컨 입력으로 채널(0~99)을 관리하는 **TVController**를 TDD로 개발하기 위해, **Git 환경 문서**, **Cursor AI 규칙**, **작업 보고서**를 정리·작성하였다.

---

## 2. 프로젝트 핵심

| 항목 | 내용 |
|:-----|:-----|
| 목적 | 리모컨 → 채널 처리 → 외부 `Tuner.setCH()` 연동 |
| 숫자 키 | 0 ~ 9 |
| 채널 범위 | 0 ~ 99 |
| Tuner | 업체 제공 — Mock/Fake만 사용, 구현·내부 테스트 금지 |
| 개발 대상 | `TVController`, `TVControllerTest` |

**기능 범위 (README)**  
숫자 채널 변경 · 선호채널 추가/다음 · 채널검색 · 업/다운(검색 유무 분기)

---

## 3. 본 세션 산출물

| 산출물 | 역할 |
|:-------|:-----|
| `github_setting.txt` | Git/GitHub 터미널 작업·명령 기록 |
| `.cursorrules` | Cursor AI TDD·리팩토링 규칙 |
| `REPORT/TDD_TV_01_작업보고서.md` | 상세 보고서 |
| `REPORT/TDD_TV_01_요약본_PDF용.md` | 본 PDF용 요약 |

---

## 4. Cursor AI 규칙 요약

| 구분 | 규칙 |
|:-----|:-----|
| 역할 | 시니어 Java — 레거시 QA/리팩토링 |
| 스택 목표 | Java 21 · Maven · JUnit 5 · JaCoCo |
| 절대 | `Item` 수정 금지 · 채널 0~99 · Tuner Mock/Fake만 |
| 테스트 | Given–When–Then · `@ParameterizedTest` · 키=입력 |
| 리팩토링 | Green 후에만 · 매직 넘버 상수화 |

---

## 5. Git / GitHub 요약

- **저장소** antihu99/TDD_TV_01
- **브랜치** `main` · `dev` · `prompting`(작업용)
- **흐름** dev 커밋·push → main pull → `prompting` 브랜치 생성
- **권장** `target/` ignore · `git push -u origin prompting`

---

## 6. 현황 · 이슈

| 영역 | 상태 |
|:-----|:-----|
| TVController | KEY_1·KEY_OK만 처리, README 시나리오 대부분 미구현 |
| remoteKey | KEY_1, KEY_OK만 정의 |
| mvn test | 14 tests — 10 failures, 1 error |
| TunerTest | Mock stub 미설정 (참고용) |
| pom.xml | Java 1.8 (규칙 목표 21과 불일치) |

---

## 7. 향후 작업 (Top 5)

1. pom.xml → Java 21 + JaCoCo
2. remoteKey 0~9 및 기능 키 확장
3. TVControllerTest — GWT + Parameterized + FakeTuner
4. README 시나리오 순차 TDD (Red → Green → Refactor)
5. prompting 브랜치 커밋·push, 병합 전 mvn test Green

---

## 8. 결론

환경·규칙·문서화 단계를 마쳤으며, **Controller TDD 구현**이 다음 단계이다.  
상세: `REPORT/TDD_TV_01_작업보고서.md`

---

*TDD_TV_01 · PDF 요약본 · 2026-05-19*
