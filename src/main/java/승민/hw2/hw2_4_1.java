package 승민.hw2;

import java.util.Scanner;

public class hw2_4_1 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = scanner.nextInt();
    }

    for (int i = 0; i < n - 1; i++) {
      int maxIndex = i;
      for (int j = i + 1; j < n; j++) {
        if (arr[j] > arr[maxIndex]) {
          maxIndex = j;
        }
      }
      int temp = arr[i];
      arr[i] = arr[maxIndex];
      arr[maxIndex] = temp;
    }

    for (int i = 0; i < n; i++) {
      System.out.print(arr[i] + " ");
    }
  }
}
