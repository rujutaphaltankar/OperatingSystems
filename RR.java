import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Queue;

class Process{
    String PID;
    int AT,BT,CT,TAT,WT,RT,RBT;
    boolean added;
}

public class RR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

        for(int i=0;i<n;i++){
            p[i] = new Process();
            p[i].PID = "P " + (i+1);

            System.out.println("Enter details for " + p[i].PID);

            System.out.print("Arrival time: ");
            p[i].AT = sc.nextInt();
            System.out.print("Burst time: ");
            p[i].BT = sc.nextInt();

            p[i].RBT = p[i].BT;
        }

        System.out.print("Enter time quantum: ");
        int tq = sc.nextInt();

        Queue<Integer> queue = new ArrayDeque<>();

        String[] gantt = new String[1000];
        int[] end = new int[1000];

        int time = 0;
        int completed = 0;
        int k = 0;

        // add first arrived process
        while(completed < n){
            
            //add all process that have arrived
            for(int i=0;i<n;i++){
                if(!p[i].added && p[i].AT <= time){
                    queue.add(i);
                    p[i].added = true;
                }
            }

            //cpu is idle
            if(queue.isEmpty()){
                time++;
                continue;
            }

            //next process
            int index = queue.poll();

            //response time
            if(p[index].RBT == p[index].BT){
                p[index].RT = time - p[index].AT;
            }

            int runTime;

            if(p[index].RBT > tq){
                runTime = tq;
            }else{
                runTime = p[index].RBT;
            }

            //run process
            time = time + runTime;
            p[index].RBT = p[index].RBT - runTime;

            //store gantt
            gantt[k] = p[index].PID;
            end[k] = time;
            k++;

            //processes that arrived during execution
            for(int i=0;i<n;i++){
                if(!p[i].added && p[i].AT <= time){
                    queue.add(i);
                    p[i].added = true;
                }
            }

            //if process is finished
            if(p[index].RBT == 0){
                p[index].CT = time;
                p[index].TAT = p[index].CT - p[index].AT;
                p[index].WT = p[index].TAT - p[index].BT;

                completed++;
            }
            else{
                queue.add(index);
            }
        }

        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT\tRT"); 
        System.out.println("------------------------------------------------");

        for(int i=0;i<n;i++){
            System.out.println(
                p[i].PID + "\t" + 
                p[i].AT  + "\t" + 
                p[i].BT + "\t" + 
                p[i].CT + "\t" + 
                p[i].TAT + "\t" +
                p[i].WT + "\t" +
                p[i].RT
            );
        }

        //gantt chart
        System.out.println("\nGantt Chart");
        System.out.print("|");

        for(int i=0;i<k;i++){
            System.out.print("  " + gantt[i] + " |");
        }
        System.out.println();
        System.out.print("0");

        for(int i=0;i<k;i++){
            System.out.print("  " + end[i]);
        }

        System.out.println();
        sc.close();
    }

}