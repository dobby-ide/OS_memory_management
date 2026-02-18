import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        List<Job> jobs = JobLoader.loadJobs("jobs_data_txt/jobs_table1.txt");

        Memory memory = new Memory(20);

        Simulation simulation = new Simulation(jobs, memory);

        simulation.run();

        System.out.println("END");


//        for (Job job : jobs){
//            System.out.println("Job ID: " + job.jobId +
//                    " | Start: " + job.startTime +
//                    " | Duration: " + job.duration +
//                    " | Size: " + job.size);
//        }
    }
}
