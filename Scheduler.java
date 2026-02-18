import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


//
//  This class pretends to act like an OS scheduler,
//  keeping the all the jobs organized according
//  to their states (SLEEPING, RUNNING, NEW, FINISHED*/
public class Scheduler {
    List<Job> allJobs = new ArrayList<>();
    List<Job> newJobs = new ArrayList<>();
    List<Job> runningJobs = new ArrayList<>();
    List<Job> finishedJobs = new ArrayList<>();
    List<String[]> reportRows = new ArrayList<>();
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
                if (memory.allocateWorstFit(job)) {
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

    public void collectSnapshot(int currentTime) {
        String timeCol = String.valueOf(currentTime);

        String freeBlocksCol = memory.getFreeBlocksString();
        String runningCol = runningJobs.stream()
                .map(j -> String.valueOf(j.jobId))
                .collect(Collectors.joining(" "));

        // Convert memory to a neat visual string
        String memoryCol = formatMemory(memory.getMemoryArray(), 10); // 10 frames per line

        reportRows.add(new String[]{timeCol, freeBlocksCol, runningCol, memoryCol});
    }

    private String formatMemory(int[] mem, int framesPerLine) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mem.length; i++) {
            sb.append(mem[i] == 0 ? "." : mem[i]);
            sb.append(" ");

        }
        return sb.toString().trim();
    }

    public void printFinalReport() {
        // Reasonable fixed widths
        int timeWidth = 6;
        int freeWidth = 50;
        int runningWidth = 20;
        int memoryWidth = 50; // frames per line

        // Print header
        System.out.printf("%-" + timeWidth + "s | %-" + freeWidth + "s | %-" + runningWidth + "s | %s\n",
                "TIME", "FREE BLOCKS", "RUNNING JOBS", "MEMORY");
        System.out.println("-".repeat(timeWidth + freeWidth + runningWidth + memoryWidth + 9));

        for (String[] row : reportRows) {
            String memoryStr = row[3];
            // Split memory into chunks of memoryWidth
            int start = 0;
            while (start < memoryStr.length()) {
                int end = Math.min(start + memoryWidth, memoryStr.length());
                String memChunk = memoryStr.substring(start, end);

                if (start == 0) {
                    // First line, print everything
                    System.out.printf("%-" + timeWidth + "s | %-" + freeWidth + "s | %-" + runningWidth + "s | %s\n",
                            row[0], row[1], row[2], memChunk);
                } else {
                    // Subsequent lines, print memory only
                    System.out.printf("%-" + timeWidth + "s | %-" + freeWidth + "s | %-" + runningWidth + "s | %s\n",
                            "", "", "", memChunk);
                }
                start = end;
            }
        }
    }




    public List<Job> getAllJobs() {
        return allJobs;
    }

    public Memory getMemory() {
        return memory;
    }
}
