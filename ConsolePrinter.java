import java.util.List;

//
// helps in visualizing the defragmentation of the memory as the clock interrupt increase*/
public class ConsolePrinter {
    public void printMemoryState (Memory memory){
        System.out.println("Memory state: ");
        int[] frames = memory.getFrames();
        for (int i = 0; i < frames.length; i++){
            System.out.println(frames[i] + "  ");
        }
        System.out.println();
    }

    public void printJobStates(List<Job> jobs){
        System.out.println("Jobs state: ");
        for (Job job : jobs){
            System.out.println("Job " + job.jobId + " = " + job.currentState);
        }
        System.out.println();
    }
}
