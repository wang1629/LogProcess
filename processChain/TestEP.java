

public class TestEP {

    static Entry e1  = new Entry("c1", 1001, Entry.START, 1);
    static Entry e2  = new Entry("c2", 1002, Entry.START, 1);
    static Entry e3  = new Entry("c2", 1003, Entry.END,   1);
    static Entry e4  = new Entry("c1", 1004, Entry.END,   1);
    static Entry e5  = new Entry("c3", 1005, Entry.START, 1);
    static Entry e6  = new Entry("c3", 1006, Entry.END,   1);
    static Entry e7  = new Entry("c4", 1007, Entry.START, 1);
    static Entry e8  = new Entry("c4", 1008, Entry.END,   1);

    static Entry e9  = new Entry("c1", 2001, Entry.START, 2);
    static Entry e10 = new Entry("c2", 2002, Entry.START, 2);
    static Entry e11 = new Entry("c2", 2003, Entry.END,   2);
    static Entry e12 = new Entry("c1", 2004, Entry.END,   2);
    static Entry e13 = new Entry("c3", 2005, Entry.START, 2);
    static Entry e14 = new Entry("c3", 2006, Entry.END,   2);
    static Entry e15 = new Entry("c4", 2007, Entry.START, 2);
    static Entry e16 = new Entry("c4", 2008, Entry.END,   2);

    static Entry e[] = new Entry[17];

    public static void buildEntryArray() {
        e[1] = e1; e[2] = e2; e[3] = e3; e[4] = e4; 
        e[5] = e5; e[6] = e6; e[7] = e7; e[8] = e8;
        e[9] = e9; e[10] = e10; e[11] = e11; e[12] = e12; 
        e[13] = e13; e[14] = e14; e[15] = e15; e[16] = e16;
    }

    public static void buildEntryArrayRandom() {
        e[1] = e2;
        e[3] = e7; 
        e[5] = e5; 
        e[7] = e8;
        e[9] = e3;
        e[11] = e1;
        e[13] = e4;
        e[15] = e6;

        e[2] = e2;
        e[4] = e7;
        e[6] = e5;
        e[8] = e8;
        e[10] = e3;
        e[12] = e1;
        e[14] = e4;
        e[16] = e6;
    }

    public static void testMatch(int i, int j) {
        MyMatch myMatch = new MyMatch();
        boolean matchRes = myMatch.match(e[i], e[j]);
//        System.out.println("match(" + e[i] + "," + e[j] +")=" + matchRes);
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
        for(int i=1; i<=16; i++) {
            ep.receiveNewEntry(e[i]);
        }
    }

    public static void main(String args[]) {
        testEPRandom();
    }
}
