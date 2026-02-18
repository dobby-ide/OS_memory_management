import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobLoader {

    public static List<Job> loadJobs(String fileName) throws FileNotFoundException {
        List<Job> jobs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;

            while ((line = br.readLine()) != null){
                if(line.trim().isEmpty()) continue;

                String[] parts = line.split(",");

                int jobId = Integer.parseInt(parts[0].trim());
                int startTime = Integer.parseInt(parts[1].trim());
                int duration = Integer.parseInt(parts[2].trim());
                int size = Integer.parseInt(parts[3].trim());

                Job job = new Job(jobId, startTime, duration, size);
                jobs.add(job);

            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return jobs;
    }
}
