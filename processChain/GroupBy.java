
public abstract class GroupBy<T, I> {

    private Set<int> intSet = mew HashSet<int>();
    private Set<I> iSet = mew HashSet<I>();

    public abstract I groupBy(T t);
    public abstract int toIndex(I i);

    public applyGroupBy(T t);

}
