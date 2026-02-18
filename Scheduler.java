import java.util.ArrayList;
import java.util.List;


//
//  This class pretends to act like an OS scheduler,
//  keeping the all the jobs organized according
//  to their states (SLEEPING, RUNNING, NEW, FINISHED*/
public class Scheduler {
    private List<Job> allJobs;
    private List<Job> newJobs;
    private List<Job> runningJobs;
    private List<Job> sleepingJobs;
    private List<Job> finishedJobs;
    private Memory memory;

    public Scheduler(List<Job> jobs, Memory memory) {

        this.memory = memory;
        this.allJobs = new ArrayList<>(jobs);
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
        // updates running jobs
        List<Job> finishedThisTick = new ArrayList<>();
        for (Job job: runningJobs){
            job.remainingTime--;
            if(job.remainingTime <= 0){
                if(jobShouldSleep(job, allJobs)) {
                    job.currentState = State.SLEEP;
                    sleepingJobs.add(job);
                } else {
                    job.currentState = State.END;
                    finishedJobs.add(job);
                    memory.deallocate(job);
                }
                finishedThisTick.add(job);
            }
        }
        runningJobs.removeAll(finishedThisTick);

        // wakes sleeping job if their scheduled time is now (for some potential interrupts)
        List<Job> wakingJobs = new ArrayList<>();
        for (Job job : sleepingJobs){
            if(job.startTime == currentTime){
                if(memory.allocateFirstFit(job)){
                    job.currentState = State.RUNNING;
                    runningJobs.add(job);
                    wakingJobs.add(job);
                }
            }
        }
        sleepingJobs.removeAll(wakingJobs);
    }

    public boolean allJobsFinished(){
        return newJobs.isEmpty() &&
                runningJobs.isEmpty() &&
                sleepingJobs.isEmpty();
    }

    private boolean jobShouldSleep(Job job, List<Job> allJobs) {
        if (job.currentState == State.END) return false;
        // check if there is a future interval for this Job ID
        // this part is extremely simulated and probably has little to do with how a scheduler works
        for (Job j : allJobs) {
            if (j.jobId == job.jobId && j.startTime > job.startTime) {
                return true;
            }
        }
        return false;
    }



}
