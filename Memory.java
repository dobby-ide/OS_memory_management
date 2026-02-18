import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


    // FIRST-FIT
    public boolean allocateFirstFit(Job job) {

        int i = 0;
        while (i < frames.length) {

            if (frames[i] == 0) {
                int start = i;
                int size = 0;

                while (i < frames.length && frames[i] == 0) {
                    size++;
                    i++;
                }

                Hole hole = new Hole(start, size);

                if (hole.size >= job.size) {
                    List<Integer> allocated = new ArrayList<>();
                    for (int j = 0; j < job.size; j++) {
                        frames[hole.start + j] = job.jobId;
                        allocated.add(hole.start + j);
                    }
                    job.allocateFrames(allocated);
                    return true;
                }

            } else {
                i++;
            }
        }

        return false;
    }


    // BEST-FIT
    public boolean allocateBestFit(Job job) {

        Hole bestHole = null;

        int i = 0;
        while (i < frames.length) {

            if (frames[i] == 0) {
                int start = i;
                int size = 0;

                while (i < frames.length && frames[i] == 0) {
                    size++;
                    i++;
                }

                Hole hole = new Hole(start, size);

                if (hole.size >= job.size) {
                    if (bestHole == null || hole.size < bestHole.size) {
                        bestHole = hole;
                    }
                }

            } else {
                i++;
            }
        }

        if (bestHole != null) {
            List<Integer> allocated = new ArrayList<>();
            for (int j = 0; j < job.size; j++) {
                frames[bestHole.start + j] = job.jobId;
                allocated.add(bestHole.start + j);
            }
            job.allocateFrames(allocated);
            return true;
        }

        return false;
    }


    // WORST-FIT
    public boolean allocateWorstFit(Job job) {

        Hole worstHole = null;

        int i = 0;
        while (i < frames.length) {

            if (frames[i] == 0) {
                int start = i;
                int size = 0;

                while (i < frames.length && frames[i] == 0) {
                    size++;
                    i++;
                }

                Hole hole = new Hole(start, size);

                if (hole.size >= job.size) {
                    if (worstHole == null || hole.size > worstHole.size) {
                        worstHole = hole;
                    }
                }

            } else {
                i++;
            }
        }

        if (worstHole != null) {
            List<Integer> allocated = new ArrayList<>();
            for (int j = 0; j < job.size; j++) {
                frames[worstHole.start + j] = job.jobId;
                allocated.add(worstHole.start + j);
            }
            job.allocateFrames(allocated);
            return true;
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

    public String getFreeBlocksString() {
        List<Integer> freeIndexes = new ArrayList<>();
        for (int i = 0; i <frames.length;i++){
            if (frames[i] == 0){
                freeIndexes.add(i);
            }
        }
        return freeIndexes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }

    public String getMemoryLayoutString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == 0) {
                sb.append(". ");
            } else {
                sb.append(frames[i]).append(" ");
            }
        }

        return sb.toString().trim();
    }

    public int[] getMemoryArray() {
        int[] memArray = new int[frames.length];
        for (int i = 0; i < frames.length; i++) {
            memArray[i] = frames[i]; // or 0 if empty
        }
        return memArray;
    }
}
