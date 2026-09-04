import java.util.*;

class Process {
String PID;
int AT, BT, CT, TAT, WT, RT;
boolean completed;
}

public class SJF {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter number of processes: ");
    int n = sc.nextInt();

    Process[] p = new Process[n];
    String[] gantt = new String[n];

    // Input
    for (int i = 0; i < n; i++) {

        p[i] = new Process();
        p[i].PID = "P" + (i + 1);

        System.out.println("\nEnter details for Process " + p[i].PID);
        System.out.print("Enter arrival time: ");
        p[i].AT = sc.nextInt();
        System.out.print("Enter burst time: ");
        p[i].BT = sc.nextInt();
    }

    int time = 0;
    int completed = 0;

    // SJF Scheduling
    while (completed < n) {

        int index = -1;

        // Find process with shortest burst time
        for (int i = 0; i < n; i++) {

            if (!p[i].completed && p[i].AT <= time) {

                if (index == -1 || p[i].BT < p[index].BT) {
                    index = i;
                }
            }
        }

        // If no process has arrived
        if (index == -1) {
            time++;
            continue;
        }

        // Store process for Gantt Chart
        gantt[completed] = p[index].PID;

        // Run process
        time = time + p[index].BT;

        // Calculate times
        p[index].CT = time;
        p[index].TAT = p[index].CT - p[index].AT;
        p[index].WT = p[index].TAT - p[index].BT;
        p[index].RT = p[index].WT;

        // Mark process as completed
        p[index].completed = true;
        completed++;
    }

    // Display final output
    System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT\tRT");
    System.out.println("--------------------------------------------");

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
        System.out.print("  " + gantt[i] + "  |");
    }

    System.out.println();
    System.out.print("0");

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {

            if (gantt[i].equals(p[j].PID)) {
                System.out.print("     " + p[j].CT);
            }
        }
    }

    System.out.println();
    sc.close();
}

}