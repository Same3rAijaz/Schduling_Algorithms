import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fair implements Algorithm {
    private List<Task> taskList;
    private int quantum;

    public Fair(List<Task> queue) {
        this.taskList = queue;
        this.quantum = 10;
    }

    @Override
    public void schedule() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("FairShare_Output.txt"));
        writer.write("Fair Share Scheduling\n\n");

        // Initialize the user list
        Map<String, List<Task>> userList = new HashMap<>();

        // Divide the processes into users
        for (Task task : taskList) {
            String user = task.getUser();
            if (userList.containsKey(user)) {
                userList.get(user).add(task);
            } else {
                List<Task> newList = new ArrayList<>();
                newList.add(task);
                userList.put(user, newList);
            }
        }

        int currentTime = 0;
        int totalProcesses = taskList.size();
        int completedProcesses = 0;
        List<Task> completedProcessesList = new ArrayList<>();

        // Run the scheduling algorithm
        while (completedProcesses < totalProcesses) {
            // Iterate over each user's processes
            for (String user : userList.keySet()) {
                List<Task> userProcesses = userList.get(user);
                int userQuantum = (int) Math.ceil(quantum * ((double) userProcesses.size() / taskList.size()));

                // Run the user's processes for their fair share of time
                for (int i = 0; i < userQuantum; i++) {
                    for (int j = 0; j < userProcesses.size(); j++) {
                        Task currentProcess = userProcesses.get(j);

                        // Check if the process has arrived and not completed
                        if (currentProcess.getArrivalTime() <= currentTime && !currentProcess.isCompleted()) {
                            int remainingTime = currentProcess.getBurst();

                            // Run the process for the quantum or until completion
                            while (remainingTime > 0) {
                                if (remainingTime <= userQuantum - i) {
                                    currentTime += remainingTime;
                                    currentProcess.setCompletionTime(currentTime);
                                    currentProcess.setTurnAroundTime(currentTime - currentProcess.getArrivalTime());
                                    currentProcess.setWaitTime(currentProcess.getTurnaroundTime(currentTime - currentProcess.getArrivalTime()) - currentProcess.getBurst());
                                    completedProcessesList.add(currentProcess);
                                    completedProcesses++;
                                    currentProcess.setCompleted(true);
                                    remainingTime = 0;
                                } else {
                                    currentTime += userQuantum - i;
                                    remainingTime -= userQuantum - i;
                                    currentProcess.setLastExecutionTime(currentTime);
                                    i = userQuantum - 1;
                                }
                            }
                        }

                        // Check if all processes for the user are completed
                        boolean allProcessesCompleted = true;
                        for (Task task : userProcesses) {
                            if (!task.isCompleted()) {
                                allProcessesCompleted = false;
                                break;
                            }
                        }
                        if (allProcessesCompleted) {
                            break;
                        }
                    }
                }
            }
        }

        // Print the completed processes list
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        System.out.println("Fair Share Scheduling:");
        writer.write("Fair Share Scheduling:\n\n");
        for (Task process : completedProcessesList) {
            totalWaitingTime += process.getWaitTime();
            totalTurnaroundTime += process.getTurnAroundTime();
            System.out.println(process.toString());
            writer.write(process.toString());
        }
        double avgWaitingTime = totalWaitingTime / completedProcessesList.size();
        double avgTurnaroundTime = totalTurnaroundTime / completedProcessesList.size();
        System.out.println("Average waiting time: " + avgWaitingTime + " ms");
        System.out.println("Average turnaround time: " + avgTurnaroundTime+" ms");
        writer.write("Average waiting time: " + avgWaitingTime + " ms\n");
        writer.write("Average turnaround time: " + avgTurnaroundTime + " ms\n\n");
        writer.close();
    }

    @Override
    public Task pickNextTask() {
        return null;
    }
}
