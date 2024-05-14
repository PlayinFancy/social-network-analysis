import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private String filePath;

    public FileReader(String filePath) {
        this.filePath = filePath;
    }

    // Reads each file line by line and processes it
    public void displayFileContents(SocialNetwork network) {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                if (parts.length > 1) {
                    String personName = parts[0];
                    network.addPerson(personName);
                    for (int i = 1; i < parts.length; i++) {
                        network.addFollowing(personName, parts[i]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
