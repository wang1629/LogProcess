
public class TestProcessChain {

    static LogStream<Entry> logStream = new LogStream<Entry>();;

    public static void main(String args[]) {

        FeedThread feeder = new FeedThread(logStream);
        feeder.start();

        ProcessChain pc = new ProcessChain();
        pc.setLogStream(logStream);
        pc.init();
        pc.setMatchFunction(new MyMatch());

        //pc.addFilter(new MyFilter());

        EntryProcessorManager<Integer> epm = new EntryProcessorManager<Integer>();
        epm.setGroupBy(new MyGroupBy());

        pc.setEntryProcessorMananger(epm);

        pc.process(); // never return.

    }
}



class MyGroupBy implements GroupBy<Entry, Integer> {
    public Integer applyGroupBy(Entry entry) {
        return entry.getRequestID();
    }
}

class MyMatch implements MatchFunction<Entry> {
    public boolean match(Entry e1, Entry e2) {
        if(!(e1.getCounter().equals(e2.getCounter())))
            return false;
        if(e1.getRequestID() != e2.getRequestID()) 
            return false;
        if(e1.getTraceFlag() + e2.getTraceFlag() == 3) /* bad. magic number */
            return true;
        return false;
    }
}

class MyFilter implements Filter<Entry> {
    public boolean filter(Entry t) {
        return t.getRequestID() == 1;
    }
}

class FeedThread extends Thread {

    Entry e1  = new Entry("c1", 1001, Entry.START, 1);
    Entry e2  = new Entry("c2", 1002, Entry.START, 1);
    Entry e3  = new Entry("c2", 1003, Entry.END,   1);
    Entry e4  = new Entry("c1", 1004, Entry.END,   1);
    Entry e5  = new Entry("c3", 1005, Entry.START, 1);
    Entry e6  = new Entry("c3", 1006, Entry.END,   1);
    Entry e7  = new Entry("c4", 1007, Entry.START, 1);
    Entry e8  = new Entry("c4", 1008, Entry.END,   1);

    Entry e9  = new Entry("c1", 2001, Entry.START, 2);
    Entry e10 = new Entry("c2", 2002, Entry.START, 2);
    Entry e11 = new Entry("c2", 2003, Entry.END,   2);
    Entry e12 = new Entry("c1", 2004, Entry.END,   2);
    Entry e13 = new Entry("c3", 2005, Entry.START, 2);
    Entry e14 = new Entry("c3", 2006, Entry.END,   2);
    Entry e15 = new Entry("c4", 2007, Entry.START, 2);
    Entry e16 = new Entry("c4", 2008, Entry.END,   2);

    Entry e[] = new Entry[17];


    LogStream<Entry> logStream;

    public FeedThread(LogStream<Entry> logStream) {
        this.logStream = logStream;
    }

    public void buildEntryArray() {
        e[1] = e1; e[2] = e2; e[3] = e3; e[4] = e4; 
        e[5] = e5; e[6] = e6; e[7] = e7; e[8] = e8;
        e[9] = e9; e[10] = e10; e[11] = e11; e[12] = e12; 
        e[13] = e13; e[14] = e14; e[15] = e15; e[16] = e16;
    }

    public void buildEntryArrayRandom() {
        e[1] = e2;        e[2] = e10;
        e[3] = e7;        e[4] = e15;
        e[5] = e5;        e[6] = e13;
        e[7] = e8;        e[8] = e16;
        e[9] = e3;        e[10] = e11;
        e[11] = e1;       e[12] = e9;
        e[13] = e4;       e[14] = e12;
        e[15] = e6;       e[16] = e14;
    }

    public void run() {
        System.out.println("feed thread start");
        buildEntryArrayRandom();
        for(int i=1; i<=16; i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}

            System.out.println("------Feed " + e[i]);
            logStream.receiveNew(e[i]);
        }
    }
}

