
import java.util.LinkedList;

/* HierarchyStepResult is used to model the stack-style call path */
/* Currently, we only consider some simple scenario :( */
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
public class HierarchyStepResult extends StepResult {

    private LinkedList<HierarchyStepResult> hierarchyStepList = new LinkedList<HierarchyStepResult>();

    public void addSubStep(HierarchyStepResult subStep) {
        hierarchyStepList.add(subStep);
    }

}

