import java.util.ArrayList;
import java.util.List;

public class Memory {
    private int totalFrames;
    private int[] frames;

    public Memory(int totalFrames) {
        this.totalFrames = totalFrames;
        this.frames = new int[totalFrames];
    }

    // This method implements First-fit in a contiguous memory allocation.
    // It checks for consecutive frames in memory.
    // If, from any frame of the memory, consecutive spaces are found such that job.size() fits,
    // the frames will be marked as used by that jobID,
    // and the job stores the allocated "addresses" in which it is running*/
    public boolean allocateFirstFit(Job job){
        List<Integer> allocated = new ArrayList<>();
        int consecutive = 0; // though allocateFirstFit

        for (int i =0 ; i < frames.length; i++){
            // checks if some place in memory there are free consecutive frames to fit the job size
            if(frames[i] == 0){
                consecutive++;
                allocated.add(i);
                if(consecutive == job.size){
                    for (int idx : allocated){
                        frames[idx] = job.jobId;
                    }
                    job.allocateFrames(allocated);
                    return true;
                }

            } else {
                // resets the consecutive counter and the list of frames that were previously stored
                allocated.clear();
                consecutive = 0;
            }
        }

        return false;
    }

    public void deallocate(Job job){
        for (int frameIndex : job.allocatedFrames){
            frames[frameIndex] = 0;
        }
        job.deallocateFrames();
    }

    public void printMemoryState() {
        System.out.print("Memory: [");
        for (int i = 0; i < frames.length; i++) {
            System.out.print(frames[i]);
            if (i != frames.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public int[] getFrames() {
        return frames;
    }
}
