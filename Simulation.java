import java.io.Console;
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
        ConsolePrinter printer = new ConsolePrinter();

        while (!scheduler.allJobsFinished()){
            System.out.println("Tick: " + currentTime);

            scheduler.tick(currentTime);
            scheduler.collectSnapshot(currentTime);

            printer.printMemoryState(scheduler.getMemory());
            printer.printJobStates(scheduler.getAllJobs());
            currentTime++;
        }
        scheduler.printFinalReport();
        System.out.println("----- all jobs finished");
    }



}
