public class Memory {
    private int totalFrames;
    private int[] frames;

    public Memory(int totalFrames) {
        this.totalFrames = totalFrames;
        this.frames = new int[totalFrames];
    }
}
