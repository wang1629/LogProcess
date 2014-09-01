

class Entry {
    int x, y;
    Entry(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString() {
        return "" + x + "," + y;
    }
}


public class Test {

    public static void testCache() {
        Cache<Entry> cache = new Cache<Entry>();

        Entry el[] = new Entry[5];
        for(int i=0; i<5; i++) {
            el[i] = new Entry(100+i, 200+i);
        }

        cache.addToCache(el[0]);
        cache.addToCache(el[1]);
        cache.addToCache(el[2]);
        Entry ef = new Entry(105, 209);
        Entry found = cache.getFromCache( ef,(new CompareByKey<Entry>() {
            public boolean compare(Entry e1, Entry e2){
                //return e1.x == e2.x;
                return e1.y == e2.y;
            }
        }));

        System.out.println(found);

    }

    public static void main(String args[]) {

        testCache();

    }
}
