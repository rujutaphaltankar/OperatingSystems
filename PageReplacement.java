import java.util.Scanner;

public class PageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] pages = new int[50];
        int[] memory = new int[20];
        int[] lastUsed = new int[20];
        int[][] table = new int[20][50];
        char[] status = new char[50];

        int n, frames, choice;
        int i, j, k;
        int found, position;
        int hits = 0, faults = 0;
        int pointer = 0;

        System.out.print("Enter number of pages (maximum 50): ");
        n = sc.nextInt();

        if (n < 1 || n > 50) {
            System.out.println("Invalid number of pages.");
            return;
        }

        System.out.println("Enter the page reference string:");
        for (i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        System.out.print("Enter number of frames (maximum 20): ");
        frames = sc.nextInt();

        if (frames < 1 || frames > 20) {
            System.out.println("Invalid number of frames.");
            return;
        }

        System.out.println("\n--- PAGE REPLACEMENT MENU ---");
        System.out.println("1. FCFS");
        System.out.println("2. LRU");
        System.out.println("3. Optimal");
        System.out.print("Enter your choice: ");
        choice = sc.nextInt();

        if (choice < 1 || choice > 3) {
            System.out.println("Invalid choice.");
            return;
        }

        for (i = 0; i < frames; i++) {
            memory[i] = -1;
            lastUsed[i] = -1;
        }

        for (i = 0; i < n; i++) {
            found = 0;
            position = -1;

            // Check whether the page is already in memory
            for (j = 0; j < frames; j++) {
                if (memory[j] == pages[i]) {
                    found = 1;
                    position = j;
                    break;
                }
            }

            if (found == 1) {
                status[i] = 'H';

                if (choice == 2) {
                    lastUsed[position] = i;
                }
            } 
            else {
                status[i] = 'M';
                faults++;

                // Find an empty frame
                for (j = 0; j < frames; j++) {
                    if (memory[j] == -1) {
                        position = j;
                        break;
                    }
                }

                // FCFS (FIFO)
                if (position == -1 && choice == 1) {
                    position = pointer;
                }

                // LRU
                if (position == -1 && choice == 2) {
                    position = 0;

                    for (j = 1; j < frames; j++) {
                        if (lastUsed[j] < lastUsed[position]) {
                            position = j;
                        }
                    }
                }

                // Optimal
                if (position == -1 && choice == 3) {
                    int farthest = -1;

                    for (j = 0; j < frames; j++) {
                        int nextUse = -1;

                        for (k = i + 1; k < n; k++) {
                            if (memory[j] == pages[k]) {
                                nextUse = k;
                                break;
                            }
                        }

                        if (nextUse == -1) {
                            position = j;
                            break;
                        }

                        if (nextUse > farthest) {
                            farthest = nextUse;
                            position = j;
                        }
                    }
                }

                memory[position] = pages[i];

                if (choice == 1) {
                    pointer++;

                    if (pointer == frames) {
                        pointer = 0;
                    }
                }

                if (choice == 2) {
                    lastUsed[position] = i;
                }
            }

            // Save frame contents after each page request
            for (j = 0; j < frames; j++) {
                table[j][i] = memory[j];
            }
        }

        // Display result table
        System.out.println();

        if (choice == 1) {
            System.out.println("FCFS PAGE REPLACEMENT");
        } else if (choice == 2) {
            System.out.println("LRU PAGE REPLACEMENT");
        } else {
            System.out.println("OPTIMAL PAGE REPLACEMENT");
        }

        System.out.printf("%-12s", "Page");

        for (i = 0; i < n; i++) {
            System.out.printf("%-5d", pages[i]);
        }

        System.out.println();

        for (i = 0; i < frames; i++) {
            System.out.printf("%-12s", "Frame " + (i + 1));

            for (j = 0; j < n; j++) {
                if (table[i][j] == -1) {
                    System.out.printf("%-5s", "-");
                } else {
                    System.out.printf("%-5d", table[i][j]);
                }
            }

            System.out.println();
        }

        System.out.printf("%-12s", "H / M");

        for (i = 0; i < n; i++) {
            System.out.printf("%-5c", status[i]);

            if (status[i] == 'H') {
                hits++;
            }
        }

        System.out.println();

        System.out.println("\nTotal Hits = " + hits);
        System.out.println("Total Page Faults (Misses) = " + faults);

        sc.close();
    }
}
