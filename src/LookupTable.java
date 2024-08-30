import java.util.HashMap;

public final class LookupTable {
    public static volatile LookupTable lookupTable;
    private HashMap<String, String> portProtocolLookup;
    private HashMap<String, String> protocolNumberLookup;

    LookupTable(){
    }

    public static LookupTable getLookupTable(){
        if (lookupTable != null) {
            return lookupTable;
        }
        synchronized (LookupTable.class){
            if(lookupTable == null){
                lookupTable = new LookupTable();
                lookupTable.portProtocolLookup = new HashMap<>();
                lookupTable.protocolNumberLookup = new HashMap<>();
            }
            return lookupTable;
        }
    }

    void addPortProtocolEntry(String key, String value){
        portProtocolLookup.put(key, value);
    }

    String getPortProtocolEntry(String key){
        return portProtocolLookup.get(key);
    }

    void addProtocolNumberEntry(String key, String value){
        protocolNumberLookup.put(key, value);
    }

    String getProtocolNumberEntry(String key){
        return protocolNumberLookup.get(key);
    }
}
