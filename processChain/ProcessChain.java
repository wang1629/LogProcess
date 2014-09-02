

class ProcessChain {

    private LogStream<Entry> logStream;
    private FilterChain<Entry> filterChain;
    private GroupBy groupBy;

    private LinkedList<EntryProcessor> entryProcessorList;


    public void init() {
        logStream = new LogStream<Entry>();
        filterChain = new FilterChain();
        entryProcessorList = new LinkedList<EntryProcessor>();
    }

    public void addFilters(Filter filter) {
        filterChain.addNewFilter(filter);
    }

    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    public void dispatchToEntryProcessor(Entry entry) {
        // TODO may be discard.
    }

    public EntryProcessor dispatch(Entry entry) {
        EntryProcessor ep;
        return ep;
    }

    public void process() {

        while(true) {
            Entry entry = logStream.next();
            boolean discard = filterChain.apply(entry);
            if(discard) {
                continue;
            }

            EntryProcessor ep = dispatch(entry);

            ep.receiveNewEntry(entry);
//            Result result = ep.generateResult();
//            reusltQueue.add(result);

        }

    }


}
