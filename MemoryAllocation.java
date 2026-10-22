import java.util.Scanner;

public class MemoryAllocation {

    // First Fit
    static void firstFit(int[] blocks, int blockCount, int[] processes, int processCount) {
        int[] remaining = new int[100];
        int[] allocation = new int[100];

        for (int i = 0; i < blockCount; i++)
            remaining[i] = blocks[i];

        for (int i = 0; i < processCount; i++) {
            allocation[i] = -1;

            for (int j = 0; j < blockCount; j++) {
                if (remaining[j] >= processes[i]) {
                    allocation[i] = j;
                    remaining[j] -= processes[i];
                    break;
                }
            }
        }

        System.out.println("\nFirst Fit:");
        System.out.println("Process\tSize\tBlock");

        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + (i + 1) + "\t" + processes[i] + "\t");

            if (allocation[i] == -1)
                System.out.print("Not Allocated");
            else
                System.out.print("B" + (allocation[i] + 1));

            System.out.println();
        }
    }

    // Best Fit
    static void bestFit(int[] blocks, int blockCount, int[] processes, int processCount) {
        int[] remaining = new int[100];
        int[] allocation = new int[100];

        for (int i = 0; i < blockCount; i++)
            remaining[i] = blocks[i];

        for (int i = 0; i < processCount; i++) {
            allocation[i] = -1;
            int best = -1;

            for (int j = 0; j < blockCount; j++) {
                if (remaining[j] >= processes[i]) {
                    if (best == -1 || remaining[j] < remaining[best]) {
                        best = j;
                    }
                }
            }

            if (best != -1) {
                allocation[i] = best;
                remaining[best] -= processes[i];
            }
        }

        System.out.println("\nBest Fit:");
        System.out.println("Process\tSize\tBlock");

        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + (i + 1) + "\t" + processes[i] + "\t");

            if (allocation[i] == -1)
                System.out.print("Not Allocated");
            else
                System.out.print("B" + (allocation[i] + 1));

            System.out.println();
        }
    }

    // Worst Fit
    static void worstFit(int[] blocks, int blockCount, int[] processes, int processCount) {
        int[] remaining = new int[100];
        int[] allocation = new int[100];

        for (int i = 0; i < blockCount; i++)
            remaining[i] = blocks[i];

        for (int i = 0; i < processCount; i++) {
            allocation[i] = -1;
            int worst = -1;

            for (int j = 0; j < blockCount; j++) {
                if (remaining[j] >= processes[i]) {
                    if (worst == -1 || remaining[j] > remaining[worst]) {
                        worst = j;
                    }
                }
            }

            if (worst != -1) {
                allocation[i] = worst;
                remaining[worst] -= processes[i];
            }
        }

        System.out.println("\nWorst Fit:");
        System.out.println("Process\tSize\tBlock");

        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + (i + 1) + "\t" + processes[i] + "\t");

            if (allocation[i] == -1)
                System.out.print("Not Allocated");
            else
                System.out.print("B" + (allocation[i] + 1));

            System.out.println();
        }
    }

    // Next Fit
    static void nextFit(int[] blocks, int blockCount, int[] processes, int processCount) {
        int[] remaining = new int[100];
        int[] allocation = new int[100];

        for (int i = 0; i < blockCount; i++)
            remaining[i] = blocks[i];

        int start = 0;

        for (int i = 0; i < processCount; i++) {
            allocation[i] = -1;

            int count = 0;
            int j = start;

            while (count < blockCount) {
                if (remaining[j] >= processes[i]) {
                    allocation[i] = j;
                    remaining[j] -= processes[i];
                    start = j;
                    break;
                }

                j = (j + 1) % blockCount;
                count++;
            }
        }

        System.out.println("\nNext Fit:");
        System.out.println("Process\tSize\tBlock");

        for (int i = 0; i < processCount; i++) {
            System.out.print("P" + (i + 1) + "\t" + processes[i] + "\t");

            if (allocation[i] == -1)
                System.out.print("Not Allocated");
            else
                System.out.print("B" + (allocation[i] + 1));

            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int blockCount, processCount;
        int[] blocks = new int[100];
        int[] processes = new int[100];

        System.out.print("Enter number of memory blocks: ");
        blockCount = sc.nextInt();

        System.out.println("Enter sizes of memory blocks:");

        for (int i = 0; i < blockCount; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            blocks[i] = sc.nextInt();
        }

        System.out.print("\nEnter number of processes: ");
        processCount = sc.nextInt();

        System.out.println("Enter sizes of processes:");

        for (int i = 0; i < processCount; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            processes[i] = sc.nextInt();
        }

        firstFit(blocks, blockCount, processes, processCount);
        bestFit(blocks, blockCount, processes, processCount);
        worstFit(blocks, blockCount, processes, processCount);
        nextFit(blocks, blockCount, processes, processCount);

        sc.close();
    }
}
