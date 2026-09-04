import java.util.*;

class Process {
    String PID;
    int AT, BT, PR, CT, TAT, WT, RT, RBT;
    boolean completed;
    boolean started;
}

public class PriorityPreemptive {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

        // Input
        for (int i = 0; i < n; i++) {

            p[i] = new Process();
            p[i].PID = "P" + (i + 1);

            System.out.println("\nEnter details for " + p[i].PID);

            System.out.print("Arrival time: ");
            p[i].AT = sc.nextInt();

            System.out.print("Burst time: ");
            p[i].BT = sc.nextInt();

            System.out.print("Priority (Greater Number = Higher Priority): ");
            p[i].PR = sc.nextInt();

            p[i].RBT = p[i].BT;
        }

        // Gantt Chart
        String[] gantt = new String[1000];
        int[] end = new int[1000];

        int time = 0;
        int completed = 0;
        int k = 0;

        // Priority Preemptive
        while (completed < n) {

            int index = -1;
            int highestPriority = -1;

            // Find highest priority process
            for (int i = 0; i < n; i++) {

                if (!p[i].completed && p[i].AT <= time) {

                    if (p[i].PR > highestPriority) {

                        highestPriority = p[i].PR;
                        index = i;
                    }

                    // If priority is same, choose earlier arrival time
                    else if (p[i].PR == highestPriority) {

                        if (p[i].AT < p[index].AT) {
                            index = i;
                        }

                        // If arrival time is also same,
                        // choose smaller process number
                        else if (p[i].AT == p[index].AT && i < index) {
                            index = i;
                        }
                    }
                }
            }

            // CPU is idle
            if (index == -1) {
                time++;
                continue;
            }

            // Response time
            if (!p[index].started) {
                p[index].RT = time - p[index].AT;
                p[index].started = true;
            }

            // Run process for 1 unit
            time++;
            p[index].RBT--;

            // Store Gantt chart
            gantt[k] = p[index].PID;
            end[k] = time;
            k++;

            // Process finished
            if (p[index].RBT == 0) {

                p[index].completed = true;
                p[index].CT = time;
                p[index].TAT = p[index].CT - p[index].AT;
                p[index].WT = p[index].TAT - p[index].BT;

                completed++;
            }
        }

        // Output Table
        System.out.println("\nPID\tAT\tBT\tPR\tCT\tTAT\tWT\tRT");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < n; i++) {

            System.out.println(
                p[i].PID + "\t" +
                p[i].AT + "\t" +
                p[i].BT + "\t" +
                p[i].PR + "\t" +
                p[i].CT + "\t" +
                p[i].TAT + "\t" +
                p[i].WT + "\t" +
                p[i].RT
            );

        }

        // Gantt Chart
        System.out.println("\nGantt Chart");

        System.out.print("|");

        for (int i = 0; i < k; i++) {
            System.out.print("  " + gantt[i] + " |");
        }

        System.out.println();

        System.out.print("0");

        for (int i = 0; i < k; i++) {
            System.out.print("  " + end[i]);
        }

        System.out.println();

        sc.close();
    }
}