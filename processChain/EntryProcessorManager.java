
import java.util.Map;
import java.util.HashMap;

public class EntryProcessorManager<F> {
    
    private GroupBy<Entry, F>   groupBy;
    private Map<F, EntryProcessor>  epMap = new HashMap<F, EntryProcessor>();

    private static int id = 0;

    public void setGroupBy(GroupBy<Entry, F> groupBy) {
        this.groupBy = groupBy;
    }

    public boolean check() {
        if(groupBy == null) {
            System.out.println("You should set gourpBy before use EntryProcessorManager");
            return false;
        }
        return true;
    }

    public EntryProcessor dispatch(Entry entry) {
        F field = groupBy.applyGroupBy(entry);
        synchronized(this) {
            if(epMap.containsKey(field)) {
                return epMap.get(field);
            }
            EntryProcessor ep = new EntryProcessor();
            epMap.put(field, ep);
            ep.id = id;
            id++;
            return ep;
        }
    }

}

