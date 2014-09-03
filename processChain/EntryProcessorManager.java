
import java.util.Map;
import java.util.HashMap;

public class EntryProcessorManager<F> {
    
    private GroupBy<Entry, F>   groupBy;
    private Map<F, EntryProcessor>  epMap;

    public setGroupBy(GroupBy<Entry, F> groupBy) {
        this.groupBy = groupBy;
    }

    public EntryProcessor dispatch(Entry entry) {
        F field = groupBy(entry);
        synchronized(this) {
            if(epMap.contain(field)) {
                return epMap.get(field);
            }
            EntryProcessor ep = new EntryProcessor();
            epMap.put(filed, ep);
            return ep;
        }
    }

}

