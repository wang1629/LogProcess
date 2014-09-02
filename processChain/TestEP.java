
class MyMatch implements MatchFunction<Entry> {
    public boolean match(Entry e1, Entry e2) {
        if(!(e1.getCounter().equals(e2.getCounter())))
            return false;
        if(e1.getTraceFlag() + e2.getTraceFlag() == 3) /* bad. magic number */
            return true;
        return false;
    }
}


public class TestEP {

    static Entry e1 = new Entry("c1", 1001, Entry.START);
    static Entry e2 = new Entry("c2", 1002, Entry.START);
    static Entry e3 = new Entry("c2", 1003, Entry.END);
    static Entry e4 = new Entry("c1", 1004, Entry.END);
    static Entry e5 = new Entry("c3", 1005, Entry.START);
    static Entry e6 = new Entry("c3", 1006, Entry.END);
    static Entry e7 = new Entry("c4", 1007, Entry.START);
    static Entry e8 = new Entry("c4", 1008, Entry.END);

    static Entry e[] = new Entry[9];

    public static void buildEntryArray() {
        e[1] = e1; e[2] = e2; e[3] = e3; e[4] = e4;
        e[5] = e5; e[6] = e6; e[7] = e7; e[8] = e8;
    }

    public static void buildEntryArrayRandom() {
        e[1] = e4;
        e[2] = e7; 
        e[3] = e5; 
        e[4] = e1;
        e[5] = e3;
        e[6] = e8;
        e[7] = e2;
        e[8] = e6;
    }

    public static void testMatch(int i, int j) {
        MyMatch matchFunc = new MyMatch();
        boolean matchRes = matchFunc.match(e[i], e[j]);
        System.out.println("match(" + e[i] + "," + e[j] +")=" + matchRes);
    }

    public static void testMatch() {
        buildEntryArray();
        testMatch(1,2);
        testMatch(1,3);
        testMatch(1,4);
        testMatch(2,3);
        testMatch(7,8);
    }


    public static void testEP() {
        buildEntryArray();
        EntryProcessor ep = new EntryProcessor();
        MyMatch myMatch = new MyMatch();
        ep.setMatchFunction(myMatch);
        for(int i=1; i<=8; i++) {
            ep.receiveNewEntryStack(e[i]);
        }
    }

    public static void testEPRandom() {
        buildEntryArrayRandom();
        EntryProcessor ep = new EntryProcessor();
        MyMatch myMatch = new MyMatch();
        ep.setMatchFunction(myMatch);
        for(int i=1; i<=8; i++) {
            ep.receiveNewEntry(e[i]);
        }
    }

    public static void main(String args[]) {
        testEPRandom();
    }
}
