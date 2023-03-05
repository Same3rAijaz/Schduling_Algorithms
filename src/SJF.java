import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SJF implements Algorithm{
    private List<Task> taskList;
    public SJF(List<Task> queue) {
        this.taskList = queue;
    }

    @Override
    public void schedule() throws IOException {


        BufferedWriter writer = new BufferedWriter(new FileWriter("SJF_Output.txt"));
        writer.write("SJF-Scheduling\n\n");
        int currentTime = 0;
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;
        List<Task> remainingProcesses = new ArrayList<>(taskList);
        while (!remainingProcesses.isEmpty()) {
            Task shortestProcess = null;
            for (Task task : remainingProcesses) {
                if (shortestProcess == null || task.getBurst() < shortestProcess.getBurst()) {
                    shortestProcess = task;
                }
            }
            shortestProcess.setWaitTime(currentTime);
            currentTime += shortestProcess.getBurst();
            shortestProcess.setTurnAroundTime(currentTime);
            remainingProcesses.remove(shortestProcess);
            totalWaitTime += shortestProcess.getWaitTime();
            totalTurnaroundTime += shortestProcess.getTurnAroundTime();
            writer.write(shortestProcess.toString());
            System.out.println(shortestProcess.toString());
        }
        double avgWaitTime = (double) totalWaitTime / taskList.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / taskList.size();
        writer.write("\nAverage waiting time: " + avgWaitTime + " ms\n");
        writer.write("Average turnaround time: " + avgTurnaroundTime + " ms");
        System.out.println("\nAverage waiting time: " + avgWaitTime + " ms");
        System.out.println("Average turnaround time: " + avgTurnaroundTime + " ms");
        writer.close();











    }


    @Override
    public Task pickNextTask() {
        return null;
    }
}
