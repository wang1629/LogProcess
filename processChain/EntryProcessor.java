
/*
 * EntryProcessor is used to let user write his own entry process logic.
 * 
 * Some Challenge: 
 *     1. stacked step
 *     2. crossed step
 *     3. out-of-order incoming entries.
 *
 * Maybe:
 *     1. use Cache to generate step-result.
 *        everytime we get a new entry, we try to get its partner(maybe not exist now),
 *        if we get, then generate a step-result, otherwise put it into the cache waiting its partner.
 *       
 *        pseudo code:
 *          
 *        receiveNewEntry(entry) {
 *          matchedEntry = cache.hasMatch(entry);
 *          if(matchedEntry != null){
 *              stepres = generateStepResult(entry, matchedEntry);
 *              RebuildStepResultTree(stepres);
 *          }else{
 *              cache.putToCache(entry);
 *          }
 *        }
 *
 *      Note, we do not adopt this currently.
 * */

/* EntryProcessor is entry-driven style flow */
public class EntryProcessor {

    private LinkedList<HierarchyStep> topHierarchyStep;
    private Cache<Entry> cache;
    private Stack<Entry> stack;
    private MatchFunction<Entry> matchFunction;

    public void setMatchFunction(MatchFunction<Entry> matchFunction) {
        this.matchFunction = matchFunction;
    }

    public boolean applyMatch(Entry e1, Entry e2) {
        return matchFunction.match(e1, e2);
    }

    public FlowStep generateStepResult(Entry e1, Entry e2) {
        FlowStep step;
        Entry start, end;
        if(e1.getTraceFlag() == Entry::START) {
            start = e1;
            end = e2;
        } else {
            start = e2;
            end = e1;
        }
        step.startTime = start.getTimestamp();
        step.endTime = end.getTimestamp();
        step.counter = start.getCounter();
        return step;
    }

    public void receiveNewEntry(Entry entry) {

        Entry stackTop = stack.peak();

        if(entry.getTraceFlag == Entry::SINGLE) {
            // TODO
            //generateSingleResult(entry);
            //return;
        }

        // match START & END
        if(applyMatch(stackTop,entry)) {
            stack.pop();
            FlowStep step = generateStepResult(entry, stackTop);
//            ResultQueue.addResult(step);
            System.out.println("New result: " + step);
        } else {
            stack.push(entry);
        }

    }

//    sortAllStep(); /* by timestamp   */
//    buildHierarchy(); /* stream-style, increamental update(?) ??? challenge = out-of-order received */
    
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

    public String toString() {
        return counter + "(" + startTime + "~" + endTime + ")";
    }
}

/* HierarchyStep is used to model the stack-style call path */
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
class HierarchyStep extends FlowStep {

    private LinkedList<HierarchyStep> hierarchyStepList = new LinkedList<HierarchyStep>();

    public void addSubStep(HierarchyStep subStep) {
        hierarchyStepList.add(subStep);
    }

}
