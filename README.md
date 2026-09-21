# 2026-2 알고리즘 스터디 (Java)

10명이 함께 공부하며 **같이 짜는 코드**와 **각자 푸는 코드**를 한 저장소에서 관리합니다.

## 폴더 구조

```
src/main/java/
├── week04/
│   ├── shared/          # 함께 작성하는 코드
│   └── members/
│       ├── shone/       # 개인 풀이 (본인 폴더에만 작성)
│       └── ...
├── week05/
│   └── ...
└── week15/
```

- 주차별 폴더는 `week04` ~ `week15`까지 미리 만들어져 있습니다.
- 폴더는 `.gitkeep`으로 유지하고 있으니, 파일을 추가하면 `.gitkeep`은 지워도 됩니다.

## 규칙

1. **개인 코드**는 `weekXX/members/내이름/` 안에만 작성합니다. 다른 사람 폴더는 수정하지 않습니다.
2. **공동 코드**는 `weekXX/shared/`에 작성합니다.
3. 파일명은 자유롭게 작성합니다.
4. 가능하면 `main` 브랜치에 직접 push하지 않고, 브랜치를 만들어 PR로 올립니다.

## 브랜치 / 커밋 예시

| 용도 | 브랜치 이름 | 예시 |
|---|---|---|
| 개인 풀이 | `feat/이름-weekXX` | `feat/shone-week01` |
| 공동 코드 | `shared/weekXX` | `shared/week01` |

커밋 메시지는 `[weekXX] 이름: 내용` 형식으로 씁니다.

```
[week05] shone: BOJ 2750 풀이
[week05] shared: 병합 정렬 구현
```

## 작업 순서

```bash
git pull origin main
git checkout -b feat/이름-week01
# 코드 작성
git add .
git commit -m "[week01] 이름: BOJ 2750 풀이"
git push origin feat/이름-week01
# GitHub에서 PR 생성 → 리뷰어 1명 이상 확인 후 병합
```

## 리뷰

서로 다른 접근을 비교하는 것이 목적이므로 PR에 풀이 아이디어와 시간복잡도를 한두 줄로 적고, 서로 코멘트를 남깁니다.

## 참여자

| 이름 | GitHub |
|---|---|
| | |
