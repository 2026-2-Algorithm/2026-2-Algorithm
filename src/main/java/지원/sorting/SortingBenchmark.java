package 지원.sorting;

import java.nio.file.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

/**
 * 여섯 가지 정렬 알고리즘의 수행시간을 비교하는 프로그램이다.
 *
 * 비교 알고리즘:
 * 1. 삽입 정렬
 * 2. 선택 정렬
 * 3. 버블 정렬
 * 4. 병합 정렬
 * 5. 퀵 정렬
 * 6. 힙 정렬
 *
 * 입력 크기 n을 10부터 1000까지 변화시키면서 각 알고리즘을
 * 동일한 난수 배열에 실행한다.
 *
 * 공정한 측정을 위해 다음 방법을 사용한다.
 * - 모든 알고리즘에 동일한 원본 배열의 복사본을 제공한다.
 * - 배열 복사 시간은 측정에서 제외한다.
 * - JVM의 JIT 컴파일 영향을 줄이기 위해 워밍업을 수행한다.
 * - 각 조건을 21회 측정하고 중앙값을 대표값으로 사용한다.
 * - 정렬 후 결과가 실제 오름차순인지 검사한다.
 *
 * 측정 결과는 콘솔과 sorting_results.csv 파일에 기록된다.
 */

public class SortingBenchmark {
    // 측정할 입력 배열의 크기
    private static final int[] SIZES = {
            10, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000
    };

    // JVM의 JIT 컴파일 영향을 줄이기 위한 사전 실행 횟수
    private static final int WARMUP_ROUNDS = 100;

    // 각 입력 크기와 알고리즘을 반복 측정하는 횟수
    private static final int REPETITIONS = 21;

    // 실행할 때마다 동일한 난수를 생성하기 위한 고정 seed
    private static final long RANDOM_SEED = 20260929L;

    @FunctionalInterface
    interface SortAlgorithm {
        void sort(int[] array);
    }

    // 측정 결과 CSV 파일을 저장할 위치
    private static final Path OUTPUT_FILE = Path.of(
            "src",
            "main",
            "java",
            "지원",
            "sorting",
            "sorting_results.csv"
    );

    public static void main(String[] args) throws IOException {
        String[] names = {
                "Insertion", "Selection", "Bubble", "Merge", "Quick", "Heap"
        };

        // 각 정렬 알고리즘을 메서도 참조로 저장한다.
        // 같은 benchmark 코드를 여섯 알고리즘에 공통으로 적용할 수 있다.
        SortAlgorithm[] algorithms = {
                SortingBenchmark::insertionSort,
                SortingBenchmark::selectionSort,
                SortingBenchmark::bubbleSort,
                SortingBenchmark::mergeSort,
                SortingBenchmark::quickSort,
                SortingBenchmark::heapSort
        };

        // 실제 측정 전에 각 알고리즘을 여러 번 실행한다.
        // Java는 실행 중 자주 사용하는 코드를 JIT 컴파일하므로
        // 워밍업 없이 측정하면 초기 컴파일 시간이 결과에 포함될 수 있다.
        warmUp(algorithms);
        Random random = new Random(RANDOM_SEED);

        try (PrintWriter csv = new PrintWriter(
                OUTPUT_FILE.toFile(), StandardCharsets.UTF_8)) {
            csv.println("n," + String.join(",", names));
            System.out.println("단위: ns (각 조건 " + REPETITIONS + "회 중앙값)");
            System.out.println("n\t" + String.join("\t", names));

            for (int n : SIZES) {
                long[] totalTimes = new long[algorithms.length];
                long[][] measurements =
                        new long[algorithms.length][REPETITIONS];

                for (int repeat = 0; repeat < REPETITIONS; repeat++) {
                    // 이번 반복에서 모든 알고리즘이 공통으로 사용할 원본 배열
                    int[] original = createRandomArray(n, random);

                    for (int a = 0; a < algorithms.length; a++) {
                        // 모든 알고리즘에 동일한 입력을 제공한다.
                        // 이미 정렬된 배열이 다음 알고리즘에 전달되는 것을 방지한다.
                        int[] input = Arrays.copyOf(original, original.length);

                        // 배열 복사 시간은 제외하고 순수 정렬 시간만 측정한다.
                        long start = System.nanoTime();
                        algorithms[a].sort(input);
                        long end = System.nanoTime();

                        // 빠르게 실행됐더라도 정렬 결과가 틀리면 유효한 측정이 아니다.
                        if (!isSorted(input)) {
                            throw new IllegalStateException(
                                    names[a] + " 정렬 결과 오류, n=" + n);
                        }
                        measurements[a][repeat] = end - start;
                    }
                }

                for (int a = 0; a < algorithms.length; a++) {
                    // 평균은 매우 큰 이상치의 영향을 받을 수 있으므로
                    // 반복 측정 결과의 중앙값을 대표 수행시간으로 사용한다.
                    totalTimes[a] = median(measurements[a]);
                }

                StringBuilder row = new StringBuilder(String.valueOf(n));
                StringBuilder screen = new StringBuilder(String.valueOf(n));
                for (long time : totalTimes) {
                    row.append(',').append(time);
                    screen.append('\t').append(time);
                }
                csv.println(row);
                System.out.println(screen);
            }
        }

        System.out.println("측정 완료: " + OUTPUT_FILE);
    }

    private static void warmUp(SortAlgorithm[] algorithms) {
        Random warmupRandom = new Random(1234L);
        for (int round = 0; round < WARMUP_ROUNDS; round++) {
            int[] original = createRandomArray(500, warmupRandom);
            for (SortAlgorithm algorithm : algorithms) {
                int[] input = Arrays.copyOf(original, original.length);
                algorithm.sort(input);
                if (!isSorted(input)) {
                    throw new IllegalStateException("워밍업 중 정렬 오류");
                }
            }
        }
    }

    private static int[] createRandomArray(int n, Random random) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = random.nextInt(1_000_000);
        }
        return result;
    }

    private static long median(long[] values) {
        long[] copy = Arrays.copyOf(values, values.length);
        Arrays.sort(copy);
        return copy[copy.length / 2];
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) return false;
        }
        return true;
    }

    /**
     * 삽입 정렬
     *
     * 왼쪽 부분 배열이 정렬되어 있다고 가정하고,
     * 새로운 원소를 올바른 위치에 삽입한다.
     *
     * 최선: O(n)
     * 평균·최악: O(n²)
     * 안정 정렬이며 제자리 정렬이다.
     */
    public static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            // a[0...i-1]은 이미 정렬된 상태이다.
            int newItem = a[i];
            int j = i - 1;

            // newItem보다 큰 원소를 한 칸씩 오른쪽으로 이동한다.
            while (j >= 0 && a[j] > newItem) {
                a[j + 1] = a[j];
                j--;
            }

            // 비어 있는 위치에 새 원소를 삽입한다.
            a[j + 1] = newItem;
        }
    }

    /**
     * 선택 정렬
     *
     * 미정렬 구간에서 최댓값을 찾아 구간의 마지막 원소와 교환한다.
     * 한 번의 반복이 끝날 때마다 뒤쪽 원소 하나의 위치가 확정된다.
     *
     * 최선·평균·최악: O(n²)
     * 불안정 정렬이며 제자리 정렬이다.
     */
    public static void selectionSort(int[] a) {
        for (int last = a.length - 1; last >= 1; last--) {
            // a[0...last]에서 최댓값의 위치를 찾는다.
            int maxIndex = 0;
            for (int i = 1; i <= last; i++) {
                if (a[i] > a[maxIndex]) maxIndex = i;
            }

            // 찾은 최댓값을 미정렬 구간의 마지막 위치로 보낸다.
            swap(a, maxIndex, last);
        }
    }

    /**
     * 개선된 버블 정렬
     *
     * 인접한 두 원소가 역순이면 교환한다.
     * 한 번의 순회가 끝나면 가장 큰 원소가 뒤쪽에 확정된다.
     *
     * 최선: O(n), 평균·최악: O(n²)
     * 안정 정렬이며 제자리 정렬이다.
     */
    public static void bubbleSort(int[] a) {
        for (int last = a.length - 1; last >= 1; last--) {
            // 이번 순회에서 교환이 발생했는지 기록한다.
            boolean swapped = false;
            for (int i = 0; i < last; i++) {
                if (a[i] > a[i + 1]) {
                    swap(a, i, i + 1);
                    swapped = true;
                }
            }

            // 교환이 없었다면 이미 정렬된 배열이므로 조기에 종료한다.
            if (!swapped) return;
        }
    }

    /**
     * 병합 정렬
     *
     * 배열을 절반씩 재귀적으로 나눈 뒤,
     * 정렬된 두 부분 배열을 하나의 배열로 병합한다.
     *
     * 최선·평균·최악: O(n log n)
     * 안정 정렬이지만 O(n)의 보조 배열이 필요하다.
     */
    public static void mergeSort(int[] a) {
        if (a.length < 2) return;
        // 재귀 호출마다 배열을 만들지 않도록 보조 배열을 한 번만 생성한다.
        int[] temp = new int[a.length];
        mergeSort(a, temp, 0, a.length - 1);
    }

    private static void mergeSort(int[] a, int[] temp, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) >>> 1;
        mergeSort(a, temp, left, mid);
        mergeSort(a, temp, mid + 1, right);

        // 두 정렬 구간 a[left...mid], a[mid+1...right]를 병합한다.
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (a[i] <= a[j]) temp[k++] = a[i++];
            else temp[k++] = a[j++];
        }
        while (i <= mid) temp[k++] = a[i++];
        while (j <= right) temp[k++] = a[j++];
        for (i = left; i <= right; i++) a[i] = temp[i];
    }

    /**
     * 퀵 정렬
     *
     * 피벗을 기준으로 작은 원소와 큰 원소를 분할한 뒤
     * 두 부분 배열을 재귀적으로 정렬한다.
     *
     * 평균: O(n log n)
     * 최악: O(n²)
     * 일반적으로 불안정 정렬이며 제자리 정렬이다.
     */
    public static void quickSort(int[] a) {
        Random pivotRandom = new Random(987654321L);
        quickSort(a, 0, a.length - 1, pivotRandom);
    }

    private static void quickSort(
            int[] a, int left, int right, Random pivotRandom) {
        while (left < right) {
            // 정렬된 입력에서 계속 나쁜 피벗이 선택되는 것을 방지하기 위해
            // 현재 구간에서 피벗 위치를 무작위로 선택한다.
            int pivotIndex = left + pivotRandom.nextInt(right - left + 1);
            swap(a, pivotIndex, right);
            int q = partition(a, left, right);

            // 재귀 깊이를 줄이기 위해 작은 부분 배열을 먼저 재귀 처리하고,
            // 큰 부분 배열은 반복문으로 계속 처리한다.
            if (q - left < right - q) {
                quickSort(a, left, q - 1, pivotRandom);
                left = q + 1;
            } else {
                quickSort(a, q + 1, right, pivotRandom);
                right = q - 1;
            }
        }
    }

    /**
     * 피벗 이하의 원소를 왼쪽에 배치하고
     * 피벗보다 큰 원소를 오른쪽에 배치한다.
     *
     * 반환값은 피벗이 최종적으로 놓인 인덱스이다.
     */
    private static int partition(int[] a, int left, int right) {
        int pivot = a[right];
        int boundary = left - 1;
        for (int j = left; j < right; j++) {
            if (a[j] <= pivot) {
                boundary++;
                swap(a, boundary, j);
            }
        }
        swap(a, boundary + 1, right);
        return boundary + 1;
    }

    /**
     * 힙 정렬
     *
     * 배열을 최대 힙으로 만든 뒤 루트의 최댓값을 배열 끝으로 옮긴다.
     * 힙 크기를 하나씩 줄이면서 같은 작업을 반복한다.
     *
     * 최선·평균·최악: O(n log n)
     * 불안정 정렬이며 제자리 정렬이다.
     */
    public static void heapSort(int[] a) {
        // 오름차순 결과를 위해 마지막 내부 노드부터 루트 방향으로 heapify하여 최대 힙을 만든다.
        for (int root = a.length / 2 - 1; root >= 0; root--) {
            maxHeapify(a, root, a.length);
        }
        for (int end = a.length - 1; end >= 1; end--) {
            // 최대 힙의 루트는 현재 구간의 최댓값이다.
            // 루트와 마지막 원소를 교환해 최댓값의 위치를 확정한다.
            swap(a, 0, end);
            maxHeapify(a, 0, end);
        }
    }

    /**
     * root를 루트로 하는 부분 트리가 최대 힙 조건을 만족하도록 복구한다.
     * 자식 중 더 큰 값과 부모를 교환하면서 아래 방향으로 이동한다.
     */
    private static void maxHeapify(int[] a, int root, int size) {
        while (true) {
            int largest = root;
            int left = 2 * root + 1;
            int right = left + 1;

            if (left < size && a[left] > a[largest]) largest = left;
            if (right < size && a[right] > a[largest]) largest = right;
            if (largest == root) return;

            swap(a, root, largest);
            root = largest;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}