import java.util.List;

public class Simulation {
    private List<Job> jobs;
    private Memory memory;
    private int currentTime;

    public Simulation(List<Job> jobs, Memory memory){
        this.jobs=jobs;
        this.memory= memory;
        this.currentTime = 0;
    }

    public void run(){
        while(!allJobsFinished()){}
    }

    private boolean allJobsFinished(){

        return false;
    }
}
