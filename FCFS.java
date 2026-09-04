import java.util.*;

class Process {
    String PID;
    int AT, BT, CT, TAT, WT, RT;
}

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

        // Input
        for (int i = 0; i < n; i++) {

            p[i] = new Process();
            p[i].PID = "P" + (i + 1);

            System.out.println("\nEnter details for process " + p[i].PID);
            System.out.print("Enter arrival time: ");
            p[i].AT = sc.nextInt();
            System.out.print("Enter burst time: ");
            p[i].BT = sc.nextInt();
        }

        // Sort processes according to Arrival Time
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                if (p[j].AT > p[j + 1].AT) {

                    Process temp = p[j];
                    p[j] = p[j + 1];
                    p[j + 1] = temp;
                }
            }
        }

        int time = 0;

        // Calculate CT, TAT, WT and RT
        //initial time as CT
        for (int i = 0; i < n; i++) {

            if (time < p[i].AT) {
                time = p[i].AT;
            }

            p[i].CT = time + p[i].BT;
            p[i].TAT = p[i].CT - p[i].AT;
            p[i].WT = p[i].TAT - p[i].BT;
            p[i].RT = p[i].WT;

            time = p[i].CT;
        }

        // Display table
        System.out.println("\n------------------------------------------------------");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT\tRT");
        System.out.println("------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.println(
                p[i].PID + "\t" +
                p[i].AT + "\t" +
                p[i].BT + "\t" +
                p[i].CT + "\t" +
                p[i].TAT + "\t" +
                p[i].WT + "\t" +
                p[i].RT
            );
        }

        // Gantt Chart
        System.out.println("\nGantt Chart");
        System.out.print("|");

        for (int i = 0; i < n; i++) {
            System.out.print("    " + p[i].PID + "    |");
        }

        System.out.println();

        // Print starting time
        System.out.print(p[0].AT);

        for (int i = 0; i < n; i++) {
            System.out.print("     " + p[i].CT);
        }

        System.out.println();
        sc.close();
    }
}