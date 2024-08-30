import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomFileReader {
    // helpers for line reads
    private String lineReader;
    private String[] lineValues;
    private final HashMap<String, ArrayList<Integer>> versionMap = new HashMap<>();
    private final LookupTable lookupTable;

    CustomFileReader(){
        lookupTable = LookupTable.getLookupTable();
    }

    void initialiseVersionMapping(String fileName) throws IOException{
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(fileName))) {
            boolean skippedHeader = false;
            while ((lineReader = br.readLine()) != null) {
                if(!skippedHeader){
                    skippedHeader = true;
                    continue;
                }
                lineValues = lineReader.split(",");
                ArrayList<Integer> indices = new ArrayList<>();
                indices.add(Integer.parseInt(lineValues[1]));
                indices.add(Integer.parseInt(lineValues[2]));
                versionMap.put(lineValues[0], indices);
            }
        }
    }

    void readLookUpTable(String fileName) throws IOException {
        // ready to read input file and create lookup hashmap
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(fileName))) {
            boolean skippedHeader = false;
            while ((lineReader = br.readLine()) != null) {
                if(!skippedHeader){
                    skippedHeader = true;
                    continue;
                }
                lineValues = lineReader.split(",");
                lookupTable.addPortProtocolEntry(lineValues[0] + "," + lineValues[1], lineValues[2]);
            }
        }
    }

    void readProtocolNumbers(String fileName) throws IOException {
        // ready to read input file and create lookup hashmap
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(fileName))) {
            boolean skippedHeader = false;
            while ((lineReader = br.readLine()) != null) {
                if(!skippedHeader){
                    skippedHeader = true;
                    continue;
                }
                lineValues = lineReader.split(",");
                if(lineValues[0].compareTo("146-252") == 0 || lineValues[0].compareTo("253") == 0 || lineValues[0].compareTo("254") == 0){
                    continue;
                }
                lookupTable.addProtocolNumberEntry(lineValues[0], lineValues[1].toLowerCase());
            }
        }
    }

    void readFlowLog(String fileName, ComputeEngine engine) throws IOException{
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(fileName))) {
            while ((lineReader = br.readLine()) != null) {
                lineValues = lineReader.split(" ");
                ArrayList<Integer> indices = versionMap.get(lineValues[0]);
                engine.compute(lineValues[indices.get(0)], lineValues[indices.get(1)]);
            }
        }
    }
}
