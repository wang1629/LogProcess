

/* EntryProcessor is entry-driven style flow */
public class EntryProcessor {

    private LinkedList<HierarchyStep> topHierarchyStep;

    sortAllStep();
    buildHierarchy();
    
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


class HierarchyStep extends FlowStep {
    
    private LinkedList<HierarchyStep> hierarchyStepList;


    
}
