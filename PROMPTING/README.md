# prompting — Cursor Session Export

이 폴더는 **prompting** 브랜치 작업 중 Cursor AI 세션의 프롬프트·대화 기록을 보관합니다.

## 파일 목록

| 파일 | 설명 |
|------|------|
| `prompts_only.md` | 사용자 프롬프트만 정리 (**15건**, 3세션) |
| `transcript_export.md` | 전체 대화 transcript (Markdown, 도구 호출 포함) |
| `03.TDD_TV_01_테스트계획_구현_보고서_prompting.md` | Session 3 대화 (테스트 계획·JaCoCo) |
| `transcript_raw.jsonl` | Cursor agent transcript 원본 (**Session 1+2 병합**) |
| `transcript_raw_session1.jsonl` | Session 1 원본만 |
| `transcript_raw_session2.jsonl` | Session 2 원본만 |
| `00.TDD_TV_01_작업보고서_prompting.md` | prompting 관련 작업 보고서 (있는 경우) |
| `README.md` | 본 안내 |

## Session 정보

| Session | ID | Prompts | 주요 산출물 |
|---------|-----|---------|-------------|
| 1 | `3b7d5ee7-d341-4044-811c-f29bda2a0a36` | 1~7 | `github_setting.txt`, `.cursorrules`, `REPORT/00`, PDF 요약 |
| 2 | `17ca3870-850a-4f1d-820c-be3e6882786f` | 8~10 | `docs/01.requirements_analysis.md`, `REPORT/01`, export |
| 3 | (Agent, 2026-05-19) | 11~15 | `docs/03`, JaCoCo·갭 테스트, `REPORT/03`, `03_*_prompting.md` |

**원본 경로**

- `.cursor/projects/.../agent-transcripts/3b7d5ee7-.../`
- `.cursor/projects/.../agent-transcripts/17ca3870-.../`

**1차 export 스냅샷**: `prompting_base/` (Session 1만, 2026-05-19 초기)

## 재 export

세션이 갱신된 경우:

1. `agent-transcripts/<session-id>/*.jsonl` 복사 → `transcript_raw_sessionN.jsonl`
2. 병합 → `transcript_raw.jsonl`
3. `prompts_only.md`, `transcript_export.md` 재생성
