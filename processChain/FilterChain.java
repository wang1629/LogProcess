

public interface Filter<T> {
    /* return true if @t should be filtered(discard) */
    public boolean filter(T t);
}

public class FilterChain<T> {

    private List< Filter<T> > filterChain = new LinkedList< Filter<T> >();

    public void addNewFilter(Filter filter) {
        filterChain.add(filter);
    }
    
    public boolean removeFilter(Filter filter) {
        return filterChain.remove(filter);
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

