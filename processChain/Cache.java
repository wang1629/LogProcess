
import java.util.List;
import java.util.LinkedList;


interface CompareByKey<T> {
    public boolean compare(T t1, T t2);
}

public class Cache<T> {

    private List<T> list = new LinkedList<T>();

    public void addToCache(T t) {
        synchronized(this) {
            list.add(t);
        }
    }

    public T getFromCache(T t, CompareByKey<T> compareObj) {
        synchronized(this) {
            for(int i=0; i<list.size(); i++) {
                T e = list.get(i);
                if(compareObj.compare(t, e)) {
                    list.remove(i);
                    return e;
                }
            }
            return null;
        }
    }
    
}

