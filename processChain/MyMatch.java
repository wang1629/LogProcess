
public class MyMatch implements MatchFunction<Entry> {
    
    public boolean match(Entry e1, Entry e2) {
//        System.out.println("match: " + e1 + "," + e2);
        if(!(e1.getCounter().equals(e2.getCounter())))
            return false;
        if(e1.getRequestID() != e2.getRequestID()) 
            return false;
        if(e1.getTraceFlag() + e2.getTraceFlag() == 3) /* bad. magic number */
            return true;
        return false;
    }
}


