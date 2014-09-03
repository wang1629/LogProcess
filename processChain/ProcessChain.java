
import java.util.LinkedList;

class ProcessChain {

    private LogStream<Entry>    logStream;
    private FilterChain<Entry>  filterChain;
    private EntryProcessorManager<?>  epManager;

    public void setLogStream(LogStream<Entry> logStream) {
        this.logStream = logStream;
    }

    public void setEntryProcessorMananger(EntryProcessorManager<?> epManager) {
        this.epManager = epManager;
    }

    public void addFilter(Filter<Entry> filter) {
        filterChain.addNewFilter(filter);
    }

    public void setGroupBy(GroupBy<Entry,?> groupBy) {
        epManager.setGroupBy(groupBy);
    }

    public void setMatchFunction(MatchFunction<Entry> myMatch) {
        epManager.setMatchFunction(myMatch);
    }

    public void init() {
        //logStream = new LogStream<Entry>();
        filterChain = new FilterChain<Entry>();
        entryProcessorList = new LinkedList<EntryProcessor>();
    }

    public EntryProcessor dispatch(Entry entry) {
        return epManager.dispatch(entry);
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

            ep.receiveNewEntry(entry);
            //Result result = ep.generateResult();
            //reusltQueue.add(result);

        }

    }


}
