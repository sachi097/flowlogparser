**Assumptions**
- Tool is required to parse all versions of the flow-logs. Ex: 2 (default), 3, 4, 5, 7 etc.
- Since flow-log defines protocol as a number, a protocol number to protocol name conversion is required and protocol-number.csv file is created by referring [here](https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml) 
- To support multi-version flow-log parsing, version_table.csv is required. This file provides three attributes
  * version - what version the flow-log record is (2, 3, 4, 5, 7 etc.)
  * dstportIndex - zero-based index of dstport in the version (basically the position where the attribute appears). For default version - it is 6.
  * protocolIndex - zero-based index of protocol in the version (basically the position where the attribute appears). For default version - it is 7.
- If input consists of only default version records then no change is required in the version_table.csv
- Sample of custom format - version 3 and 4 record is included in the flowlog.txt as per the entry in version_table.csv

**Folder structure**
- src: contains 4 classes and 2 folders
  * Main.java : Main driver of the program
  * LookupTable : Singleton class holding lookup table hashmaps and operations.
    * portProtocolLookup : hashmap for portProtocol combination counter
    * protocolNumberLookup : hashmap for protocol number to name mapping
  * CustomFileReader: Class to perform file reading operations for various input files mentioned above. File is read line by line in order to maintain memory efficient readings.
  * ComputeEngine: Class in which actual logic to determine counters for tag and port/protocol are placed. Also output file is written here.
  * input folder: contains 4 files
    * flowlog.txt : aws-flow-log records go here
    * lookup_table.csv : lookup mappings go here (csv file first line should be: dstport,protocol,tag)
    * protocol-numbers.csv : this file has been used for protocol number to protocol name mapping and is downloaded from [here](https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml)
    * version_table.csv : this file is used to specify version to dstport and protocol index (csv file first line should be: version,dstportIndex,protocolIndex)
  * result folder: contains 1 file named output.txt (generated output can be found in this file)

**Steps to run - considering all assumptions are met**
- clone repo
- make sure current directory is flowlogparser, run below commands in terminal.
``` 
  cd src
  javac Main.java
  java Main
```
- find output.txt within result folder
