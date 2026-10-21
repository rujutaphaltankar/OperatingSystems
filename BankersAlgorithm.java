import java.util.Scanner;

public class BankersAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n,m;
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        System.out.print("Enter number of resources: ");
        m = sc.nextInt();

        int total[] = new int[m];
        int allocation[][] = new int[n][m];
        int max[][] = new int[n][m];
        int need[][] = new int[n][m];
        int available[] = new int[m];

        // Enter total instances of each resource
        System.out.println("\nEnter total instances of each resource:");

        for (int j = 0; j < m; j++) {
            System.out.print("Resource " + (char)('A' + j) + ": ");
            total[j] = sc.nextInt();
        }

        // Enter Allocation
        System.out.println("\nEnter Allocation:");

        for (int i = 0; i < n; i++) {
            System.out.print("P" + i + ": ");

            for (int j = 0; j < m; j++) {
                allocation[i][j] = sc.nextInt();
            }
        }

        // Enter Max
        System.out.println("\nEnter Max:");

        for (int i = 0; i < n; i++) {
            System.out.print("P" + i + ": ");

            for (int j = 0; j < m; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        // Calculate Remaining Need
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        // Calculate initial Available
        for (int j = 0; j < m; j++) {
            int allocated = 0;

            for (int i = 0; i < n; i++) {
                allocated = allocated + allocation[i][j];
            }

            available[j] = total[j] - allocated;
        }

        // Work contains current Available resources
        int work[] = new int[m];

        for (int j = 0; j < m; j++) {
            work[j] = available[j];
        }

        boolean finish[] = new boolean[n];

        int safeSequence[] = new int[n];

        // This stores Available values for P0, P1, P2, P3...
        int availableTable[][] = new int[n][m];

        int count = 0;

        // Banker Safety Algorithm
        while (count < n) {
            boolean found = false;

            for (int i = 0; i < n; i++) {
                if (finish[i] == false)  {
                    boolean canRun = true;

                    // Check if Need <= Available
                    for (int j = 0; j < m; j++) {
                        if (need[i][j] > work[j])  {
                            canRun = false;
                            break;
                        }
                    }

                    if (canRun == true) {
                        safeSequence[count] = i;

                        // Store current Available
                        // according to safe sequence
                        for (int j = 0; j < m; j++) {
                            availableTable[count][j] = work[j];
                        }

                        // Process completes and releases Allocation
                        for (int j = 0; j < m; j++) {
                            work[j] = work[j] + allocation[i][j];
                        }

                        finish[i] = true;
                        count++;
                        found = true;
                    }
                }
            }

            if (found == false) {
                break;
            }
        }

        // Check whether system is safe
        if (count == n) {
            System.out.println("\nSystem is in SAFE STATE.");

            System.out.printf("\n------------------------------------------------------------");
            System.out.printf("%-10s %-18s %-18s %-18s %-18s%n",
                    "Process",
                    "Allocation",
                    "Max",
                    "Available",
                    "Remaining Need");

            System.out.println("------------------------------------------------------------");

            for (int i = 0; i < n; i++) {
                System.out.printf("%-10s ", "P" + i);

                // Allocation
                for (int j = 0; j < m; j++) {
                    System.out.print(allocation[i][j] + " ");
                }

                System.out.print("        ");

                // Max
                for (int j = 0; j < m; j++) {
                    System.out.print(max[i][j] + " ");
                }

                System.out.print("        ");

                // Available
                for (int j = 0; j < m; j++) {
                    System.out.print(availableTable[i][j] + " ");
                }

                System.out.print("        ");

                // Remaining Need
                for (int j = 0; j < m; j++) {
                    System.out.print(need[i][j] + " ");
                }

                System.out.println();
            }

            System.out.println("------------------------------------------------------------");

            // Display Safe Sequence
            System.out.print("\nSafe Sequence: ");

            for (int i = 0; i < n; i++) {
                System.out.print("P" + safeSequence[i]);

                if (i < n - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();
        }
        else {
            System.out.println("\nSystem is in UNSAFE STATE.");
            System.out.println("Safe sequence does not exist.");
        }

        sc.close();
    }
}