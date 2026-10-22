import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n, head, size, dir;
        int i, j, temp, cur, total;
        int[] a = new int[50];

        System.out.print("Enter number of requests: ");
        n = sc.nextInt();

        System.out.print("Enter requests: ");
        for (i = 0; i < n; i++)
            a[i] = sc.nextInt();

        System.out.print("Enter initial head position: ");
        head = sc.nextInt();

        System.out.print("Enter disk size: ");
        size = sc.nextInt();

        System.out.print("Enter direction (0=Left, 1=Right): ");
        dir = sc.nextInt();

        // FIFO
        cur = head;
        total = 0;
        System.out.print("\nFIFO: " + cur);

        for (i = 0; i < n; i++) {
            total += Math.abs(cur - a[i]);
            cur = a[i];
            System.out.print(" -> " + cur);
        }

        System.out.println("\nTotal movement = " + total);

        // SSTF
        int[] done = new int[50];
        cur = head;
        total = 0;
        System.out.print("\nSSTF: " + cur);

        for (i = 0; i < n; i++) {
            int min = 9999, pos = -1;

            for (j = 0; j < n; j++) {
                if (done[j] == 0 && Math.abs(cur - a[j]) < min) {
                    min = Math.abs(cur - a[j]);
                    pos = j;
                }
            }

            done[pos] = 1;
            total += Math.abs(cur - a[pos]);
            cur = a[pos];
            System.out.print(" -> " + cur);
        }

        System.out.println("\nTotal movement = " + total);

        // Bubble sort
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }

        // SCAN
        cur = head;
        total = 0;
        System.out.print("\nSCAN: " + cur);

        if (dir == 1) {
            for (i = 0; i < n; i++) {
                if (a[i] >= head) {
                    total += Math.abs(cur - a[i]);
                    cur = a[i];
                    System.out.print(" -> " + cur);
                }
            }

            total += Math.abs(cur - (size - 1));
            cur = size - 1;
            System.out.print(" -> " + cur);

            for (i = n - 1; i >= 0; i--) {
                if (a[i] < head) {
                    total += Math.abs(cur - a[i]);
                    cur = a[i];
                    System.out.print(" -> " + cur);
                }
            }
        } else {
            for (i = n - 1; i >= 0; i--) {
                if (a[i] <= head) {
                    total += Math.abs(cur - a[i]);
                    cur = a[i];
                    System.out.print(" -> " + cur);
                }
            }

            total += cur;
            cur = 0;
            System.out.print(" -> 0");

            for (i = 0; i < n; i++) {
                if (a[i] > head) {
                    total += Math.abs(cur - a[i]);
                    cur = a[i];
                    System.out.print(" -> " + cur);
                }
            }
        }

        System.out.println("\nTotal movement = " + total);

        // C-SCAN
        cur = head;
        total = 0;
        System.out.print("\nC-SCAN: " + cur);

        if (dir == 1) {
            for (i = 0; i < n; i++) {
                if (a[i] >= head) {
                    total += Math.abs(cur - a[i]);
                    cur = a[i];
                    System.out.print(" -> " + cur);
                }
            }

            total += Math.abs(cur - (size - 1));
            cur = size - 1;
            System.out.print(" -> " + cur);

            total += size - 1;
            cur = 0;
            System.out.print(" -> 0");

            for (i = 0; i < n; i++) {
                if (a[i] < head) {
                    total += Math.abs(cur - a[i]);
                    cur = a[i];
                    System.out.print(" -> " + cur);
                }
            }
        } else {
            for (i = n - 1; i >= 0; i--) {
                if (a[i] <= head) {
                    total += Math.abs(cur - a[i]);
                    cur = a[i];
                    System.out.print(" -> " + cur);
                }
            }

            total += cur;
            cur = 0;
            System.out.print(" -> 0");

            total += size - 1;
            cur = size - 1;
            System.out.print(" -> " + cur);

            for (i = n - 1; i >= 0; i--) {
                if (a[i] > head) {
                    total += Math.abs(cur - a[i]);
                    cur = a[i];
                    System.out.print(" -> " + cur);
                }
            }
        }

        System.out.println("\nTotal movement = " + total);

        sc.close();
    }
}
