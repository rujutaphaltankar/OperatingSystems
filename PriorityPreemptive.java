import java.util.*;

class Process {
    int pid,at,bt,ct,tat,wt,rt,pr,response;
    boolean started = false;
    boolean completed = false;
}

public class PriorityPreemptive {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process p[] = new Process[n];

        // Input

        for (int i = 0; i < n; i++) {

            p[i] = new Process();

            p[i].pid = i + 1;

            System.out.println("\nProcess P" + p[i].pid);

            System.out.print("Arrival Time: ");
            p[i].at = sc.nextInt();

            System.out.print("Burst Time: ");
            p[i].bt = sc.nextInt();

            System.out.print("Priority (Greater Number = Higher Priority): ");
            p[i].pr = sc.nextInt();

            p[i].rt = p[i].bt;
        }


        // Gantt Chart Arrays

        String gantt[] = new String[1000];
        int times[] = new int[1001];

        int g = 0;

        int time = 0;
        int completed = 0;

        times[0] = 0;


        while (completed < n) {

            int idx = -1;

            int highestPriority = -1;


            // Find Highest Priority Process

            for (int i = 0; i < n; i++) {

                if (!p[i].completed && p[i].at <= time) {

                    if (p[i].pr > highestPriority) {

                        highestPriority = p[i].pr;
                        idx = i;
                    }

                    else if (p[i].pr == highestPriority) {

                        if (p[i].at < p[idx].at) {

                            idx = i;
                        }

                        else if (p[i].at == p[idx].at &&
                                p[i].pid < p[idx].pid) {

                            idx = i;
                        }
                    }
                }
            }


            // CPU Idle

            if (idx == -1) {

                gantt[g] = "Idle";

                g++;

                time++;

                times[g] = time;

                continue;
            }


            // Response Time

            if (!p[idx].started) {

                p[idx].response = time - p[idx].at;

                p[idx].started = true;
            }


            // Execute for 1 Unit

            gantt[g] = "P" + p[idx].pid;

            g++;

            time++;

            p[idx].rt--;

            times[g] = time;


            // Process Completed

            if (p[idx].rt == 0) {

                p[idx].completed = true;

                completed++;

                p[idx].ct = time;

                p[idx].tat = p[idx].ct - p[idx].at;

                p[idx].wt = p[idx].tat - p[idx].bt;
            }
        }


        // ---------------- Gantt Chart ----------------

        System.out.println("\nGantt Chart\n");


        // Top Line

        System.out.print("+");

        for (int i = 0; i < g; i++) {

            System.out.print("--------+");
        }

        System.out.println();


        // Process Line

        System.out.print("|");

        for (int i = 0; i < g; i++) {

            System.out.printf("%-8s|", gantt[i]);
        }

        System.out.println();


        // Bottom Line

        System.out.print("+");

        for (int i = 0; i < g; i++) {

            System.out.print("--------+");
        }

        System.out.println();


        // Time Line

        for (int i = 0; i <= g; i++) {

            System.out.printf("%-9d", times[i]);
        }


        // ---------------- Table ----------------

        System.out.println("\n");

        System.out.println("PID\tAT\tBT\tPR\tCT\tTAT\tWT\tRT");

        double avgWT = 0;
        double avgTAT = 0;

        for (int i = 0; i < n; i++) {

            System.out.println(
                    "P" + p[i].pid + "\t"
                    + p[i].at + "\t"
                    + p[i].bt + "\t"
                    + p[i].pr + "\t"
                    + p[i].ct + "\t"
                    + p[i].tat + "\t"
                    + p[i].wt + "\t"
                    + p[i].response);

            avgWT += p[i].wt;
            avgTAT += p[i].tat;
        }

        System.out.println("\nAverage Waiting Time: " + (avgWT / n));
        System.out.println("Average Turn Around Time: " + (avgTAT / n));

        sc.close();
    }
}