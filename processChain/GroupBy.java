
import java.util.Set;
import java.util.HashSet;

public abstract class GroupBy<E, F> {  /* E for Entry, F for Field */

    Set<Integer> intSet = new HashSet<Integer>();
    Set<F> fieldSet = new HashSet<F>();

    public abstract F groupByField(E e);
    public abstract int toIndex(F f);

    public int applyGroupBy(E e) {
        return toIndex(groupByField(e));
    }

}
