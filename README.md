# 2026-2 알고리즘 스터디 (Java)

함께 공부하며 **같이 짜는 코드**와 **각자 푸는 코드**를 한 저장소에서 관리합니다.

## 폴더 구조

```
src/main/java/
├── shared/        # 함께 작성하는 코드
├── 금하/ 
├── 선영/ 
├── 승민/
├── 지성/ 
├── 지원/ 
├── 상록/ 
├── 지안/
├── 윤진/
├── 시온/
└── 도윤/
```

- 자기 폴더 안에서는 하위 폴더, 파일명 등 **자유롭게** 구성합니다. (예: `시온/week05/Sort.java`)
- 폴더는 `.gitkeep`으로 유지하고 있으니, 파일을 추가하면 `.gitkeep`은 지워도 됩니다.

## 규칙

1. **개인 코드**는 자기 이름 폴더 안에만 작성합니다. 다른 사람 폴더는 수정하지 않습니다.
2. **공동 코드**는 `shared/`에 작성합니다.
3. 가능하면 `main` 브랜치에 직접 push하지 않고, 브랜치를 만들어 PR로 올립니다.

## 브랜치 / 커밋 예시

| 용도 | 브랜치 이름 | 예시 |
|---|---|---|
| 개인 풀이 | `feat/내용` | `feat/merge-sort` |
| 공동 코드 | `shared/내용` | `shared/merge-sort` |

커밋 메시지는 `feat/shared: 내용` 형식으로 씁니다.

```
feat: 퀵소트 구현
shared: 병합 정렬 구현
```

만약 그 외의 변경을 표현하고 싶다면 아래의 컨벤션을 참고해서 작성해주시면 됩니다.

## 커밋 컨벤션

- `feat`: 새로운 기능 추가
- `remove` : 기획 변경등으로 필요 없어진 코드
- `move` : 폴더 위치 변경
- `add`: 파일 추가
- `fix`: 버그 수정 및 파일 수정
- `docs`: 문서 수정
- `style`: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- `refactor`: 코드 리펙토링
- `test`: 테스트 코드, 리펙토링 테스트 코드 추가
- `chore`: 빌드 업무 수정, 패키지 매니저 수정
- `design` : style**만** 변경되었을 경우

## Git 사용법 (처음이라면)

### 용어 먼저

| 용어 | 뜻 |
|---|---|
| **repository (저장소)** | 코드와 변경 기록을 담아둔 프로젝트 폴더 |
| **remote / origin** | GitHub에 있는 저장소. 내 컴퓨터의 저장소(local)와 구분됩니다 |
| **branch (브랜치)** | 원본(`main`)을 건드리지 않고 작업할 수 있는 나만의 작업 갈래 |
| **commit (커밋)** | 변경 내용을 기록으로 남기는 저장 지점 |
| **push / pull** | 내 커밋을 GitHub에 올리기 / GitHub의 최신 내용을 내려받기 |
| **PR (Pull Request)** | "내 브랜치를 main에 합쳐주세요"라는 요청 |

### 처음 한 번만: 내려받기 (clone)

```bash
git clone https://github.com/2026-2-Algorithm/2026-2-Algorithm.git
cd 2026-2-Algorithm
```

처음 커밋하기 전에 이름과 이메일을 설정합니다. (GitHub 계정과 같은 이메일 권장)

```bash
git config --global user.name "내 이름"
git config --global user.email "내 이메일"
```

### 매번 하는 작업 순서

**1. 최신 상태로 맞추기 (pull)**: 작업 시작 전에 항상 먼저 합니다.

```bash
git checkout main
git pull origin main
```

**2. 내 브랜치 만들고 이동하기 (branch, checkout)**

```bash
git checkout -b feat/quick-sort
```

`-b`는 "새로 만들면서 이동"이라는 뜻입니다. 이미 있는 브랜치로 이동만 할 때는 `-b` 없이 씁니다.

```bash
git checkout feat/quick-sort
git checkout main
```

**3. 코드 작성**: 자기 폴더(예: `시온/`) 안에서 파일을 만들고 수정합니다.

**4. 바뀐 내용 확인하기 (status, diff)**

```bash
git status      # 어떤 파일이 바뀌었는지 (빨간색 = 아직 add 안 됨, 초록색 = add 됨)
git diff        # 무엇이 어떻게 바뀌었는지
```

**5. 커밋할 파일 담기 (add)**

```bash
git add 시온/week05/QuickSort.java   # 파일 하나만
git add 시온/                         # 폴더 전체
git add .                           # 모든 변경사항
```

`git add .`은 바뀐 전부를 담기 때문에, 내 폴더 밖 파일이 섞이지 않았는지 `git status`로 확인한 뒤에 쓰세요.

**6. 커밋하기 (commit)**

```bash
git commit -m "feat: 퀵소트 구현"
```

**7. GitHub에 올리기 (push)**

```bash
git push origin feat/quick-sort     # 현재 브랜치와 이름이 동일해야 합니다
```

브랜치를 처음 올릴 때 터미널에 안내 메시지가 나오면 그대로 따라 하면 됩니다.

**8. PR 만들기**: GitHub 저장소 페이지에 나오는 **Compare & pull request** 버튼을 눌러서 풀이 아이디어와 시간복잡도를 적고 요청합니다. 리뷰어 1명 이상이 확인하면 **Merge**합니다. (혼자 Merge할 수도 있지만 그래도~)

**9. 병합 후 정리**

```bash
git checkout main
git pull origin main
git branch -d feat/quick-sort   # 다 쓴 브랜치 삭제 (필수는 아닙니다)
```

### 한눈에 보는 흐름

```
pull → checkout -b → 코드 작성 → add → commit → push → PR → merge → pull
```

### 자주 쓰는 확인 명령어

| 명령어 | 하는 일 |
|---|---|
| `git status` | 지금 상태 확인 (헷갈리면 제일 먼저 실행) |
| `git branch` | 브랜치 목록과 현재 브랜치 확인 (`*` 표시가 현재) |
| `git log --oneline` | 커밋 기록을 한 줄씩 보기 (`q`로 종료) |
| `git diff` | 아직 add 하지 않은 변경 내용 보기 |
| `git restore 파일명` | 아직 add 하지 않은 파일 수정을 **버리고** 되돌리기 |
| `git restore --staged 파일명` | 잘못 add 한 파일을 다시 빼기 (수정 내용은 유지) |
| `git stash` / `git stash pop` | 작업 중인 변경을 잠깐 치워두기 / 다시 꺼내기 (브랜치를 급히 옮길 때) |

### 문제가 생겼을 때

**push가 거절됐어요 (`rejected`)**: GitHub에 내가 모르는 새 커밋이 있다는 뜻입니다. 먼저 `git pull origin 내브랜치이름`을 하고 다시 push하세요.

**충돌(conflict)이 났어요**: 같은 파일의 같은 부분을 두 사람이 고쳤을 때 생깁니다. 자기 폴더에서만 작업하면 거의 생기지 않습니다. 생겼다면 파일을 열어 아래 표시를 찾고, 남길 내용만 정리한 뒤 표시 줄을 지웁니다.

```
<<<<<<< HEAD
내 코드
=======
상대 코드
>>>>>>> main
```

정리를 마치면 `git add 파일명` → `git commit` 하면 끝납니다. 어려우면 혼자 고민하지 말고 바로 물어보세요.

**main에서 실수로 작업했어요**: 아직 커밋 전이라면 `git checkout -b feat/내용`으로 새 브랜치를 만들면 변경 내용이 그대로 따라옵니다. 그 브랜치에서 커밋하세요.

**커밋 메시지를 잘못 썼어요 (push 전)**: `git commit --amend -m "새 메시지"`

> 처음에는 `git status`를 자주 치는 습관을 들이세요. 지금 어디에 있고 무엇이 바뀌었는지 알면 대부분의 실수를 피할 수 있습니다.

## 리뷰

서로 다른 풀이를 경험하는 것이 목적이므로 PR에 풀이 아이디어와 시간복잡도를 한두 줄로 적고, 서로 코멘트를 남깁니다.

## 참여자

| 폴더 및 이름 | GitHub |
|---|---|
| 금하 | |
| 선영 | |
| 승민 | |
| 지성 | |
| 지원 | |
| 상록 | |
| 지안 | |
| 윤진 | |
| 시온 | |
| 도윤 | |
