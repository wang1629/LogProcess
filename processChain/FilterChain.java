
import java.util.LinkedList;

public class FilterChain<T> {

    private LinkedList< Filter<T> > filterChain = new LinkedList< Filter<T> >();

    public void addNewFilter(Filter<T> filter) {
        filterChain.add(filter);
    }
    
    public boolean removeFilter(Filter<T> filter) {
        return filterChain.remove(filter);
    }

    public boolean apply(T t) {
        synchronized(this) {
            for(int i=0; i<filterChain.size(); i++) {
                Filter<T> filterElement = filterChain.get(i);
                if(filterElement.filter(t)) {
                    return true;
                }
            }
            return false;
        }
    }

}

