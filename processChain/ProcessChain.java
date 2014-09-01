


class ProcessChain {

    
    private LogStream<Entry> logStream;
    private FilterChain<Entry> filterChain;

    public init() {
        logStream = new LogStream<Entry>();
        filterChain = new FilterChain();

        /* for test */
        Filter<Entry> onlyReqFilter = new RFilter<Entry>();
        filterChain.addNewFilter(onlyReqFilter);
    }

    public void process() {
        
        while(true) {
            Entry entry = logStream.next();
            boolean discard = filterChain.applyFilters(entry);
            if(discard) {
                continue;
            }



            
                                 
        }
    }


}
