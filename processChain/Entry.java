
public class Entry {

    private long timestamp;
    private String counter;
    private int traceFlag;
    private String requestID;


    public static int START = 1;
    public static int END = 2;
    public static int SINGLE = 3;

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
        return "[" + counter + ":" + timestamp + ":" + traceFlag + ":" + requestID + "]";
    }

}
