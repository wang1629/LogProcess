
public class Entry {

    private long timestamp;
    private String counter;
    private int traceFlag;
    private String requestID = "";


    public String prefixTag;
    public String hostname;
    public String processID;
    public String cpuUtilization;

    public static int START = 1;
    public static int END = 2;
    public static int SINGLE = 3;

    public Entry() { }
    public Entry(String entryLine) {
        parseFrom(entryLine);
    }

    static int count = 0;

    public void parseFrom(String entryLine) {

        String str[] = entryLine.split(" ");
        prefixTag = str[0].trim();
        String es = str[1];
        es = es.replace("\"", "");
        es = es.replace("{", "");
        es = es.replace("}", "");
        String items[] = es.split(",");
        
        /*
        System.out.println("items[0] = " + items[0]);
        System.out.println("items[1] = " + items[1]);
        System.out.println("items[2] = " + items[2]);
        System.out.println("items[3] = " + items[3]);
        System.out.println("items[4] = " + items[4]);
        System.out.println("items[5] = " + items[5]);
        */

        hostname  = items[0].split(":")[1];
        processID = items[1].split(":")[1];
        timestamp = Long.parseLong(items[2].split(":")[1]);
        traceFlag = (items[3].split(":")[1]).equals("Single")?Entry.SINGLE:((items[3].split(":")[1]).equals("Start")?Entry.START:Entry.END);
        counter   = items[4].split(":")[1];
        
        if(items[5].split(":")[0].equals("reqId")) {
            requestID = items[5].split(":")[1];
        } else if(items[5].split(":")[0].equals("cpu")) {
            cpuUtilization = items[5].split(":")[1];
        }

        if(items.length >= 7) {
            if(items[6].split(":")[0].equals("cpu")) {
                cpuUtilization = items[6].split(":")[1];
            }
        }

        count++;
        //System.out.println("(" + count + ")parseFrom return = " + this);
        
    }

    public Entry(String counter, long timestamp, int traceFlag, String requestID) {
        this.timestamp = timestamp;
        this.counter = counter;
        this.traceFlag = traceFlag;
        this.requestID = requestID;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public long getTimestamp() {
        return timestamp;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getCounter() {
        return counter;
    }

    public void setTraceFlag(int traceFlag) {
        this.traceFlag = traceFlag;
    }

    public int getTraceFlag() {
        return traceFlag;
    }

    public void setRequestId(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestID() {
        return requestID;
    }

    public String toString() {
        //return "[" + counter + ":" + timestamp + ":" + traceFlag + ":" + requestID + "]";
        return "[" + counter + ":" + timestamp + ":" + (traceFlag==Entry.SINGLE?"SINGLE":(traceFlag==Entry.START?"START":"END")) + ":" + requestID + ":" + processID + "]";
    }

}
