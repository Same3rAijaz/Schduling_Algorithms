import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FCFS implements Algorithm{
    private List<Task> taskList;
    public FCFS(List<Task> queue) {
        this.taskList = queue;
    }

    @Override
    public void schedule() throws IOException {
        int currentTime = 0;
        int time = 0;
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;
        BufferedWriter writer = new BufferedWriter(new FileWriter("fcfs_output.txt"));
        writer.write("FCFS Scheduling\n\n");
        for (Task task : taskList) {
            int waitTime = time - task.getArrivalTime();
            if (waitTime < 0) {
                waitTime = 0;
            }

            totalWaitTime += waitTime;
            time += task.getBurst();
            task.setWaitTime(currentTime);
            currentTime += task.getBurst();
            task.setTurnAroundTime(currentTime);
            writer.write(task.toString());
            System.out.println(task.toString());
            int turnaroundTime = time - task.getArrivalTime();
            totalTurnaroundTime += turnaroundTime;

        }
        double avgWaitTime = (double) totalWaitTime / taskList.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / taskList.size();
        System.out.println("Average wait time: " + avgWaitTime);
        System.out.println("Average turnaround time: " + avgTurnaroundTime);
        writer.write("Average wait time: " + avgWaitTime +"\n"+"Average turnaround time: " + avgTurnaroundTime);
        writer.close();
    }

    @Override
    public Task pickNextTask() {
        return null;
    }
}
