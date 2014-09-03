
import java.util.LinkedList;

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

    private LinkedList<HierarchyStepResult> topHierarchyStepResult;
    private Cache<Entry> cache = new Cache<Entry>();
    private MatchFunction<Entry> matchFunction;

    public int id; // for test

    public void setMatchFunction(MatchFunction<Entry> matchFunction) {
        this.matchFunction = matchFunction;
    }

    private boolean applyMatch(Entry e1, Entry e2) {
        return matchFunction.match(e1, e2);
    }

    public StepResult generateStepResult(Entry e1, Entry e2) {
        StepResult step = new StepResult();
        Entry start, end;
        if(e1.getTraceFlag() == Entry.START) {
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

    static int resCount = 0; // for test

    public StepResult receiveNewEntry(Entry entry) {
        
        //System.out.println("Recv new entry " + entry);
        Entry matchEntry = cache.hasMatch(entry, this.matchFunction);
        
        //System.out.println("matchEntry " + matchEntry);
        if(matchEntry == null) {
            cache.addToCache(entry);
            return null;
        }

        StepResult step = generateStepResult(entry, matchEntry);
        System.out.println("******************************************************(" + (++resCount) + ")***** Generate Step Result " + step);
        return step;
    }

}


