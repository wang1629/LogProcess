
public interface Filter<T> {
    /* return true if @t should be filtered(discard) */
    public boolean filter(T t);
}


