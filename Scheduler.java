import java.util.ArrayList;
import java.util.List;


//
//  This class pretends to act like an OS scheduler,
//  keeping the all the jobs organized according
//  to their states (SLEEPING, RUNNING, NEW, FINISHED*/
public class Scheduler {
    private List<Job> newJobs;
    private List<Job> runningJobs;
    private List<Job> sleepingJobs;
    private List<Job> finishedJobs;
    private Memory memory;

    public Scheduler(List<Job> jobs, Memory memory) {

        this.memory = memory;
        this.newJobs = new ArrayList<>(jobs);
        this.runningJobs = new ArrayList<>();
        this.sleepingJobs = new ArrayList<>();
        this.finishedJobs = new ArrayList<>();
    }

    public void tick(int currentTime){
        // moves new jobs that start now into the memory and remove the job from the newJobs list
        List<Job> toStart = new ArrayList<>();

        for (Job job : newJobs ){
            if(job.startTime == currentTime){
                toStart.add(job);
            }
        }

        for (Job job: toStart){
            if (memory.allocateFirstFit(job)){
                job.currentState = State.RUNNING;
                runningJobs.add(job);
            } else {
                // logic to add job in waiting list if memory not available
            }
            newJobs.remove(job);
        }
    }
}
