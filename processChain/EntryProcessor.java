

/* EntryProcessor is entry-driven style flow */
public class EntryProcessor {

    private LinkedList<HierarchyStep> topHierarchyStep;

    receiveNewEntry();
    sortAllStep(); /* by timestamp   */
    buildHierarchy(); /* stream-style, increamental update(?) ??? challenge = out-of-order received */
    
}


class FlowStep {
    
    public long startTime;
    public long endTime;
    public String counter;

    public boolean equals(LeafStep step) {
        return  counter.equals(step.counter);
    }
    public long interval() {
        return endTime - startTime;
    }
}

/* HierarchyStep is used to model the stack-style call path */
/* Currently, we only consider some simple scenario */
/* not consider the cross-step (maybe can be treated as sibling step)
 *   |  foo.START
 *   |  bar.START
 *   |  foo.END
 *   |  bar.END
 */
/*not consider:
 *   | foo.START($)
 *   | -- |
 *        | bar.START
 *        | bar.END
 *        | foo.END($)
 *   | -- |
 *   |
 *
 * Notice: start and end of @foo is not in the same call level.
 * */
class HierarchyStep extends FlowStep {

    private LinkedList<HierarchyStep> hierarchyStepList;

}
