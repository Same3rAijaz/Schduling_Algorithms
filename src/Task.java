/**
 * Task to be scheduled by the scheduling alogrithm.
 *
 * Each task is represented by
 *
 *  String name - a task name, not necessarily unique
 *
 *  int tid - unique task identifier
 *
 *  int priority - the relative priority of a task where a higher number indicates
 *  higher relative priority.
 *
 *  int burst - the CPU burst of this this task
 */

import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Comparable<Task>
{
    // the representation of each task
    private String name;
    private int tid;
    private int priority;
    private int burst;

    private int waitTime;
    private int turnaroundTime;
    /**
     * We use an atomic integer to assign each task a unique task id.
     */
    private static AtomicInteger tidAllocator = new AtomicInteger();
    private int currentTime;
    private boolean is_complete;
    private int arrival_time;
    private int set_last_executime;
    private int User = 0;
    public Task(String name, int priority, int burst) {
        this.name = name;
        this.priority = priority;
        this.burst = burst;
        this.waitTime = 0;
        this.turnaroundTime = 0;
        this.tid = tidAllocator.getAndIncrement();
        this.currentTime = 0;
        this.is_complete=false;
        this.arrival_time = 0;
        this.set_last_executime = 0;

    }

    /**
     * Appropriate getters
     */
    public String getName() {
        return name;
    }
    public int getWaitTime() {
        return waitTime;
    }

    public int getTurnaroundTime(int i) {
        return turnaroundTime;
    }
    public int getTurnAroundTime() {
        return turnaroundTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getTid() {
        return tid;
    }

    public int getPriority() {
        return priority;
    }

    public int getBurst() {
        return burst;
    }

    /**
     * Appropriate setters
     */
    public int setPriority(int priority) {
        this.priority = priority;

        return priority;
    }
    public void setTurnAroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int setBurst(int burst) {
        this.burst = burst;

        return burst;
    }

    /**
     * We override equals() so we can use a
     * Task object in Java collection classes.
     */
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof Task))
            return false;

        /**
         * Otherwise we are dealing with another Task.
         * two tasks are equal if they have the same tid.
         */
        Task rhs = (Task)other;
        return (this.tid == rhs.tid) ? true : false;
    }

    public String toString() {
        return
                "Name: " + name + "\n" +
                        "Tid: " + tid + "\n" +
                        "Priority: " + priority + "\n" +
                        "Burst: " + burst + "\n\n\n";
    }

    public void setCompletionTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void setCompleted(boolean b) {
        this.is_complete = b;
    }

    public boolean isCompleted() {
        return this.is_complete;
    }

    public int getArrivalTime() {
        return this.arrival_time;
    }

    public void setLastExecutionTime(int currentTime) {
       this.set_last_executime = currentTime;
    }

    @Override
    public int compareTo(Task o) {
        return 0;
    }

    public String getUser() {
        User+=1;
        return String.valueOf(User);
    }
}