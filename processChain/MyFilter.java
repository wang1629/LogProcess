
public class MyFilter implements Filter<Entry> {
    public boolean filter(Entry t) {
        return t.getRequestID() == 1;
    }
}
