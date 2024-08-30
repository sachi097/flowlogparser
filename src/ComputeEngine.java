import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ComputeEngine {
    HashMap<String, Integer> tagMap;
    HashMap<String, Integer> portProtocolMap;
    private final LookupTable lookupTable;

    ComputeEngine(){
        tagMap = new HashMap<>();
        portProtocolMap = new HashMap<>();
        lookupTable = LookupTable.getLookupTable();
    }

    void addTagEntry(String key){
        Integer value = tagMap.getOrDefault(key, 0);
        tagMap.put(key, value + 1);
    }

    void addPortProtocolEntry(String key){
        Integer value = portProtocolMap.getOrDefault(key, 0);
        portProtocolMap.put(key, value + 1);
    }

    void compute(String port, String protocolNumber){
        String protocolName = lookupTable.getProtocolNumberEntry(protocolNumber);
        String tag = lookupTable.getPortProtocolEntry(port + "," + protocolName);
        if(tag == null){
            tag = "Untagged";
        }
        int protocol = Integer.parseInt(protocolNumber);
        if(protocol >= 146 && protocol <= 253){
            protocolName =  "UnnamedProtocol";
            tag = "UnnamedProtocolTag";
        }
        addTagEntry(tag);
        addPortProtocolEntry(port + "," + protocolName);
    }

    void writeTagCount(FileWriter fileWriter) {
        tagMap.forEach((k, v) -> {
            String lineToWrite = k + "," + v + "\n";
            try {
                fileWriter.write(lineToWrite);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void writePortProtocolCount(FileWriter fileWriter){
        portProtocolMap.forEach((k, v) -> {
            String lineToWrite = k + "," + v + "\n";
            try {
                fileWriter.write(lineToWrite);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
