import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RR implements Algorithm{
    private List<Task> taskList;

    public RR(List<Task> queue) {this.taskList = queue;
    }

    @Override
    public void schedule() throws IOException {
        int currentTime = 0;
        int totalProcesses = taskList.size();
        int completedProcesses = 0;
        List<Task> completedProcessesList = new ArrayList<>();
        BufferedWriter writer = new BufferedWriter(new FileWriter("RR_Output.txt"));

        // Run the scheduling algorithm
        while (completedProcesses < totalProcesses) {
            // Iterate over the list of processes
            for (int i = 0; i < taskList.size(); i++) {
                Task currentProcess = taskList.get(i);

                // Check if the process has arrived
                if (currentProcess.getArrivalTime() <= currentTime && !currentProcess.isCompleted()) {
                    int remainingTime = currentProcess.getBurst();

                    // Run the process for the time quantum or until completion
                    while (remainingTime > 0) {
                        int timeQuantum = 10;
                        if (remainingTime <= timeQuantum) {
                            currentTime += remainingTime;
                            currentProcess.setCompletionTime(currentTime);
                            currentProcess.setTurnAroundTime(currentTime - currentProcess.getArrivalTime());
                            currentProcess.setWaitTime(currentProcess.getTurnaroundTime(currentTime - currentProcess.getArrivalTime()) - currentProcess.getBurst());
                            completedProcessesList.add(currentProcess);
                            completedProcesses++;
                            currentProcess.setCompleted(true);
                            remainingTime = 0;
                        } else {
                            currentTime += timeQuantum;
                            remainingTime -= timeQuantum;
                            currentProcess.setLastExecutionTime(currentTime);
                        }
                    }
                }
            }
        }

        // Print the completed processes list
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        System.out.println("Round-robin Scheduling:");
        writer.write("Round-robin Scheduling:\n\n");
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

    @Override
    public Task pickNextTask() {
        return null;
    }
}
