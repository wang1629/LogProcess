
public interface GroupBy<E, F> {  /* E for Entry, F for Field */
    public F applyGroupBy(E entry);
}
