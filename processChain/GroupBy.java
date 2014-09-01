

public interface GroupBy<E, F> {  /* E for Entry, F for Field */

    private Set<int> intSet = mew HashSet<int>();
    private Set<F> iSet = mew HashSet<F>();

    public abstract F groupBy(E e);
    public abstract int toIndex(F i);

    public applyGroupBy(E e);

}
