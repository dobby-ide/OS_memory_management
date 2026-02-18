import java.util.List;

public class Simulation {
    private Scheduler scheduler;
    private int currentTime;

    public Simulation(List<Job> jobs, Memory memory){
        this.scheduler = new Scheduler(jobs, memory);
        this.currentTime = 0;
    }

    public void run(){
        System.out.println("simulation has started -----");

        while (!scheduler.allJobsFinished()){
            System.out.println("Tick: " + currentTime);

            scheduler.tick(currentTime);

            currentTime++;
        }
        System.out.println("----- all jobs finished");
    }


}
