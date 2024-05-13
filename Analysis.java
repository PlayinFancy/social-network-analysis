import java.io.IOException;

// main file reading the argument for the file and sending it to the reader class
public class Analysis {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Analysis <inputFile>");
            return;
        }

        String inputFile = args[0];
        FileReader fileReader = new FileReader(inputFile);

        try {
            fileReader.displayFileContents();
        } catch (IOException e) {
            // printing out error incase file is not readable
            System.out.println("Error: " + e.getMessage());
        }
    }
}
