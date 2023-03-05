import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Priority implements Algorithm{
    private List<Task> processes;
    public Priority(List<Task> queue) {
        this.processes = queue;
    }

    @Override
    public void schedule() throws IOException {
        int currentTime = 0;
        int totalProcesses = processes.size();
        int completedProcesses = 0;
        List<Task> completedProcessesList = new ArrayList<>();

        // Run the scheduling algorithm
        while (completedProcesses < totalProcesses) {
            // Sort the list of processes by priority

            Task currentProcess = null;

            // Find the process with the highest priority that has arrived
            for (int i = 0; i < processes.size(); i++) {
                Task process = processes.get(i);
                if (process.getArrivalTime() <= currentTime && !completedProcessesList.contains(process)) {
                    currentProcess = process;
                    break;
                }
            }

            // Check if a process has been found
            if (currentProcess != null) {
                // Run the process to completion
                int remainingTime = currentProcess.getBurst();
                currentTime += remainingTime;
                currentProcess.setCompletionTime(currentTime);
                currentProcess.getTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                currentProcess.setWaitTime(currentProcess.getTurnaroundTime(currentTime - currentProcess.getArrivalTime()) - currentProcess.getBurst());
                completedProcessesList.add(currentProcess);
                completedProcesses++;
            } else {
                // No process is available, advance the time
                currentTime++;
            }
        }

        // Print the completed processes list
        System.out.println("Priority Scheduling:");
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        BufferedWriter writer = new BufferedWriter(new FileWriter("Priority_Output.txt"));
        writer.write("Priority-Scheduling\n\n");
        for (Task process : completedProcessesList) {
            totalWaitingTime += process.getWaitTime();
            totalTurnaroundTime += process.getTurnAroundTime();
            System.out.println(process.toString());
            writer.write(process.toString());
        }
        double avgWaitingTime = totalWaitingTime / completedProcessesList.size();
        double avgTurnaroundTime = totalTurnaroundTime / completedProcessesList.size();
        System.out.println("Average waiting time: " + avgWaitingTime + "ms");
        System.out.println("Average turnaround time: " + avgTurnaroundTime+"ms");
        writer.write("Average waiting time: " + avgWaitingTime + "ms\n");
        writer.write("Average turnaround time: " + avgTurnaroundTime + "ms\n\n");
        writer.close();
    }

    // Priority comparator class


    @Override
    public Task pickNextTask() {
        return null;
    }
}
