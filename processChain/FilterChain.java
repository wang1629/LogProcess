

public interface Filter<T> {
    /* return true if @t should be filtered(discard) */
    public boolean filter(T t);
}

public class FilterChain<T> {

    private List< Filter<T> > filterChain = new LinkedList< Filter<T> >();

    public void addNewFilter(Filter<T> filter) {
        synchronized(this) {
            filterChain.add(filter);
        }
    }
    
    public boolean removeFilter(Filter<T> filter) {
        synchronized(this) {
            return filterChain.remove(filter);
        }
    }

    public boolean apply(T t) {
        synchronized(this) {
            Iterator<T> iter = filterChain.iterator();
            while(iter.hasNext()) {
                if(iter.filter(t)) {
                    return true;
                }
            }
            return false;
        }
    }

}

