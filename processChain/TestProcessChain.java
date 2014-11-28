
public class TestProcessChain {

    static LogStream<Entry> logStream = new LogStream<Entry>();;
    static String pathPrefix = "/home/wang/work/pms/LogProcess/fakeServer/res/";
    static String masterLogPath = "master.log";
    static String sumWorkerLogPath = "sumWorker.log";
    static String sqrtWorkerLogPath = "sqrtWorker.log";

    public static void main(String args[]) {

        FeedThread masterFeeder = new FakeLogFeedThreadTailf(logStream, pathPrefix + masterLogPath);
        masterFeeder.start();
        FeedThread sumworkerFeeder = new FakeLogFeedThreadTailf(logStream, pathPrefix + sumWorkerLogPath);
        sumworkerFeeder.start();
        FeedThread sqrtworkerFeeder = new FakeLogFeedThreadTailf(logStream, pathPrefix + sqrtWorkerLogPath);
        sqrtworkerFeeder.start();

        ProcessChain pc = new ProcessChain();
        pc.setLogStream(logStream);
        pc.init();
        pc.setMatchFunction(new MyMatch());

        pc.addFilter(new MyFilter());

        EntryProcessorManager<String> epm = new EntryProcessorManager<String>();
        epm.setGroupBy(new MyGroupBy());

        pc.setEntryProcessorMananger(epm);

        pc.process(); // never return.

    }
}





//////////////////////////////////////////////////////////////////////////////


class MyGroupBy implements GroupBy<Entry, String> {
    public String applyGroupBy(Entry entry) {
        return entry.getRequestID();
    }
}

class MyMatch implements MatchFunction<Entry> {
    public boolean match(Entry e1, Entry e2) {
        if(!(e1.getCounter().equals(e2.getCounter()))) {
            return false;
        }
        
        if(!(e1.getRequestID()).equals(e2.getRequestID())) 
            return false;
        
        if(e1.getTraceFlag() + e2.getTraceFlag() == 3) /* bad. magic number */
            return true;
        return false;
    }
}

class MyFilter implements Filter<Entry> {
    public boolean filter(Entry t) {
        return (t.getRequestID().equals(""));
    }
}

