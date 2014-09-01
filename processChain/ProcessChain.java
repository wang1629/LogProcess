


class ProcessChain {

    private LogStream<Entry> logStream;
    private FilterChain<Entry> filterChain;
    private GroupBy gourpBy;

    private LinkedList<EntryProcessor> entryProcessorList;


    public void init() {
        logStream = new LogStream<Entry>();
        filterChain = new FilterChain();
        entryProcessorList = new LinkedList<EntryProcessor>();
    }

    public void addFilters(Filter filter) {
        filterChain.addNewFilter(onlyReqFilter);
    }

    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    public void process() {
        
        while(true) {
            Entry entry = logStream.next();
            boolean discard = filterChain.apply(entry);
            if(discard) {
                continue;
            }

 
        }
    }


}
