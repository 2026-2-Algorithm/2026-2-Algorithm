# Java Sorting Algorithm Benchmark

Java로 여섯 가지 정렬 알고리즘을 구현하고 입력 크기에 따른 수행시간을 비교한 프로젝트입니
다.

## 구현 알고리즘

- Insertion Sort
- Selection Sort
- Bubble Sort
- Merge Sort
- Quick Sort
- Heap Sort

## 실험 방법

- 입력 크기: `n = 10 ~ 1000`
- 데이터: 무작위 정수 배열
- 측정 함수: `System.nanoTime()`
- 워밍업: 100회
- 반복 측정: 21회
- 대표값: 중앙값
- 난수 seed: `20260929`
- 모든 알고리즘에 동일한 입력 배열 사용
- 정렬 결과 자동 검증
- 측정 결과를 `sorting_results.csv`로 저장

## 시간복잡도

### Insertion Sort

- 평균 시간복잡도: `O(n²)`
- 최악 시간복잡도: `O(n²)`
- 정렬된 앞부분에 새로운 원소를 삽입합니다.

### Selection Sort

- 평균 시간복잡도: `O(n²)`
- 최악 시간복잡도: `O(n²)`
- 미정렬 구간의 최댓값을 찾아 뒤쪽에 배치합니다.

### Bubble Sort

- 평균 시간복잡도: `O(n²)`
- 최악 시간복잡도: `O(n²)`
- 인접한 두 원소가 역순이면 교환합니다.

### Merge Sort

- 평균 시간복잡도: `O(n log n)`
- 최악 시간복잡도: `O(n log n)`
- 배열을 절반으로 나눈 뒤 정렬된 결과를 병합합니다.

### Quick Sort

- 평균 시간복잡도: `O(n log n)`
- 최악 시간복잡도: `O(n²)`
- 무작위 피벗을 기준으로 배열을 분할합니다.

### Heap Sort

- 평균 시간복잡도: `O(n log n)`
- 최악 시간복잡도: `O(n log n)`
- 최대 힙을 만든 뒤 최댓값을 배열 뒤쪽에 배치합니다.

## 실행 환경

- 운영체제: macOS Tahoe 26.6.2
- CPU: Apple M4
- CPU 아키텍처: ARM64(aarch64)
- 메모리: 16GB
- JDK: Microsoft OpenJDK 21.0.12
- IDE: IntelliJ IDEA 2024.3.2.2 Ultimate Edition
- 구현 언어: Java
- 측정 단위: ns(나노초)

## 실행 방법

1. JDK 21 이상을 설치합니다.
2. IntelliJ IDEA에서 프로젝트를 엽니다.
3. `src/SortingBenchmark.java`를 실행합니다.
4. 프로젝트 최상위 폴더에서 `sorting_results.csv`를 확인합니다.

터미널에서 실행하려면 다음 명령을 차례대로 입력합니다.

1. 컴파일: `javac src/SortingBenchmark.java`
2. 실행: `java -cp src SortingBenchmark`

## 프로젝트 구조

- `src/main/java/내이름/sorting/`
    - `SortingBenchmark.java`
    - `sorting_results.csv`
    - `README.md

측정 결과는 `src/main/java/지원/sorting/sorting_results.csv`에 저장됩니다.

IntelliJ가 생성하는 다음 파일과 폴더는 `.gitignore`를 통해 GitHub 업로드에서 제외합니다.

- `.idea/`
- `out/`
- `*.iml`
- `*.class`
- `.DS_Store`

## 측정 결과 요약

입력 크기가 증가하면서 삽입·선택·버블 정렬의 수행시간은 병합·퀵·힙 정렬보다 빠르게 증가했습
니다.

입력 크기가 `n=500`에서 `n=1000`으로 2배 증가했을 때 다음 결과가 측정되었습니다.

- `O(n²)` 정렬의 수행시간 증가: 약 3.07~3.52배
- `O(n log n)` 정렬의 수행시간 증가: 약 2.09~2.21배

이론적으로 입력 크기가 2배가 되면 `O(n²)` 알고리즘의 연산량은 약 4배가 됩니다. `O(n log n)`
알고리즘의 연산량은 약 2.22배가 됩니다. 실제 측정 결과에서도 두 알고리즘 그룹의 증가율 차이
를 확인할 수 있었습니다.

## n=1000 측정 결과

빠른 순서대로 정렬한 결과는 다음과 같습니다.

1. Quick Sort: 66,708ns
2. Merge Sort: 78,041ns
3. Insertion Sort: 83,084ns
4. Heap Sort: 85,834ns
5. Selection Sort: 325,625ns
6. Bubble Sort: 909,500ns

`n=1000`에서는 퀵 정렬이 가장 빨랐고 버블 정렬이 가장 느렸습니다.

## 측정값 분석

퀵 정렬은 `n=700`에서 일시적으로 큰 측정값이 나타났습니다.

- `n=600`: 36,833ns
- `n=700`: 111,083ns
- `n=800`: 51,917ns

`n=800`에서 수행시간이 다시 감소했으므로 입력 크기 증가에 따른 지속적인 성능 저하가 아니라
일시적인 측정 변동으로 판단했습니다.

가능한 원인은 다음과 같습니다.

- 피벗에 따른 분할 상태의 차이
- JVM의 JIT 컴파일
- 운영체제의 프로세스 스케줄링
- CPU 캐시
- 가비지 컬렉션
- 백그라운드 프로그램의 CPU 사용

따라서 개별 측정값보다 입력 크기에 따른 전체 증가 추세를 중심으로 분석해야 합니다.

## 파일 설명

### SortingBenchmark.java

여섯 정렬 알고리즘과 수행시간 측정 코드를 포함합니다.

### sorting_results.csv

입력 크기별 수행시간 측정 결과를 저장합니다.

### README.md

프로젝트의 목적, 실행 방법, 시간복잡도 및 측정 결과를 설명합니다.

### .gitignore

IntelliJ 설정 파일과 Java 컴파일 결과물이 GitHub에 업로드되지 않도록 설정합니다.

## 결론

버블 정렬과 선택 정렬은 입력 크기가 증가할수록 수행시간이 빠르게 증가했습니다. 병합 정렬,
퀵 정렬, 힙 정렬은 상대적으로 완만하게 증가했습니다.

이번 입력 범위에서는 퀵 정렬이 가장 우수한 성능을 보였습니다. 삽입 정렬은 시간복잡도가
`O(n²)`이지만 코드가 단순하고 상수 비용이 작아 `n=1000`까지는 병합 정렬과 힙 정렬에 가까운
결과를 보였습니다.

실제 알고리즘을 선택할 때는 시간복잡도뿐만 아니라 입력 크기, 입력 상태, 메모리 사용량, 구현
방식과 실행 환경도 함께 고려해야 합니다.