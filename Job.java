import java.util.ArrayList;
import java.util.List;


//* simulates job behaviours that are taken from a text file
// */
public class Job {
    int jobId;
    int duration;
    int startTime;
    int remainingTime;
    int endTime;
    State currentState;

    // Memory
    int size;
    List<Integer> allocatedFrames;

    public Job(int jobId, int startTime, int duration, int size) {
        this.jobId = jobId;
        this.duration = duration;
        this.startTime = startTime;
        this.remainingTime = duration;
        this.endTime = startTime + duration;
        this.size = size;
        this.currentState = State.NEW;
        this.allocatedFrames = new ArrayList<>();
    }

    public void allocateFrames(List<Integer> frames){
        allocatedFrames.clear();
        allocatedFrames.addAll(frames);
    }
    public void deallocateFrames() {
        allocatedFrames.clear();
    }

    public void tick() {
        if (remainingTime > 0) remainingTime--;
    }


}
