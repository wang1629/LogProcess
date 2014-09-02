
import java.util.LinkedList;

class ProcessChain {

    private LogStream<Entry> logStream;
    private FilterChain<Entry> filterChain;
    private GroupBy<Entry,Integer> groupBy;
    private LinkedList<EntryProcessor> entryProcessorList;


    // for test. should be a set of ep for each groupby and also need a mgr.
    EntryProcessor ep = new EntryProcessor();

    public void setLogStream(LogStream<Entry> logStream) {
        this.logStream = logStream;
    }

    public void init() {
        //logStream = new LogStream<Entry>();
        filterChain = new FilterChain<Entry>();
        entryProcessorList = new LinkedList<EntryProcessor>();
    }

    public void addFilter(Filter<Entry> filter) {
        filterChain.addNewFilter(filter);
    }

    public void setGroupBy(GroupBy<Entry,Integer> groupBy) {
        this.groupBy = groupBy;
    }
    public void setMatchFunction(MatchFunction<Entry> myMatch) {
        ep.setMatchFunction(myMatch);
    }

    public void dispatchToEntryProcessor(Entry entry) {
        // TODO may be discard.
    }


    public EntryProcessor dispatch(Entry entry) {
                return ep;
    }

    public void process() {

        while(true) {

            System.out.println("logStream.next()");
            Entry entry = logStream.next();
            System.out.println("logStream.next() return " + entry);
            boolean discard = filterChain.apply(entry);
            System.out.println("after filterChain " + discard);
            if(discard) {
                continue;
            }

            EntryProcessor ep = dispatch(entry);

            System.out.println("Start ep.recvNewEntry(" + entry + ")");
            ep.receiveNewEntry(entry);
            //Result result = ep.generateResult();
            //reusltQueue.add(result);

        }

    }


}
