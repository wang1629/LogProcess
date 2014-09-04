
import java.util.Map;
import java.util.HashMap;

public class EntryProcessorManager<F> {
    
    private GroupBy<Entry, F>   groupBy;
    private Map<F, EntryProcessor>  epMap = new HashMap<F, EntryProcessor>();
    private Map<Integer, F>  idMap = new HashMap<Integer, F>(); 

    private static int sequenceNuber = 1;

    public void setGroupBy(GroupBy<Entry, F> groupBy) {
        this.groupBy = groupBy;
    }

    public F getKeyById(Integer id) {
        return idMap.get(id);
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
            ep.id = sequenceNuber++;
            idMap.put(ep.id, field);
            return ep;
        }
    }

}

