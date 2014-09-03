
/* MatchFunction is used to be applied to find its partner
 * For example, A.START match A.END
 * */
public interface MatchFunction<T> {
    public boolean match(T t1, T t2);
}
