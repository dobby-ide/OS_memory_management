import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


//
//  This class pretends to act like an OS scheduler,
//  keeping the all the jobs organized according
//  to their states (SLEEPING, RUNNING, NEW, FINISHED*/
public class Scheduler {
    List<Job> allJobs = new ArrayList<>();
    List<Job> newJobs = new ArrayList<>();
    List<Job> runningJobs = new ArrayList<>();
    List<Job> finishedJobs = new ArrayList<>();
    private Memory memory;

    public Scheduler(List<Job> jobs, Memory memory) {
        this.newJobs.addAll(jobs);
        this.allJobs.addAll(jobs);
        this.memory = memory;
    }


    public void tick(int currentTime) {

        // 1. Move NEW → RUNNING if start time reached
        Iterator<Job> newIter = newJobs.iterator();
        while (newIter.hasNext()) {
            Job job = newIter.next();

            if (job.startTime <= currentTime) {
                if (memory.allocateFirstFit(job)) {
                    job.currentState = State.RUNNING;
                    runningJobs.add(job);
                    newIter.remove();
                }
            }
        }

        // 2. Execute running jobs
        Iterator<Job> runIter = runningJobs.iterator();
        while (runIter.hasNext()) {
            Job job = runIter.next();

            job.tick();

            if (job.isFinished()) {
                memory.deallocate(job);

                job.currentState = job.finalState;
                finishedJobs.add(job);

                runIter.remove();
            }
        }
    }





    public boolean allJobsFinished() {
        return newJobs.isEmpty() && runningJobs.isEmpty();
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

    public List<Job> getAllJobs() {
        return allJobs;
    }

    public Memory getMemory() {
        return memory;
    }
}
