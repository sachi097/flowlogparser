import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        String currentDirectory = System.getProperty("user.dir");

        final String INPUT_DIRECTORY = currentDirectory + File.separator + "input";
        final String OUTPUT_DIRECTORY = currentDirectory + File.separator + "result";

        // create output directory
        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists()) {
            boolean _o = outputDir.mkdir();
        }

        // instantiate compute engine
        ComputeEngine engine = new ComputeEngine();

        // instantiate custom file reader object and read lookup table and flow log
        CustomFileReader fileReader = new CustomFileReader();
        fileReader.initialiseVersionMapping(INPUT_DIRECTORY + "/version_table.csv");
        fileReader.readLookUpTable(INPUT_DIRECTORY + "/lookup_table.csv");
        fileReader.readProtocolNumbers(INPUT_DIRECTORY + "/protocol-numbers.csv");
        fileReader.readFlowLog(INPUT_DIRECTORY + "/flowlog.txt", engine);

        // generate output file
        try(FileWriter writer = new FileWriter(OUTPUT_DIRECTORY + "/output.txt", false)){
            writer.write("- Count of matches for each tag, sample o/p shown below\n");
            writer.write("Tag,Count\n");
            engine.writeTagCount(writer);
            writer.write("\n");
            writer.write("- Count of matches for each port/protocol combination\n");
            writer.write("Port,Protocol,Count\n");
            engine.writePortProtocolCount(writer);
        };
    }
}