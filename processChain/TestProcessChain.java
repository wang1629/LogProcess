
public class TestProcessChain {

    static LogStream<Entry> logStream = new LogStream<Entry>();;

    public static void main(String args[]) {

        FeedThread feeder = new FakeLogFeedThread(logStream);
        feeder.start();

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
        //if(e1.getCounter().equals("processRequest")) {
        //System.out.println("Match(" + e1 + "," + e2);
        //}
        //System.out.println("Match stepinto (" + e1.getCounter() + "," + e2.getCounter());
        if(!(e1.getCounter().equals(e2.getCounter()))) {
            //System.out.println("Match counter not equal(" + e1.getCounter() + "," + e2.getCounter());
            return false;
        }
        //System.out.println("Match reqID(" + e1.getRequestID() + "," + e2.getRequestID());
        if(!(e1.getRequestID()).equals(e2.getRequestID())) 
            return false;
        //System.out.println("Match traceflag(" + e1.getTraceFlag() + "," + e2.getTraceFlag());
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

