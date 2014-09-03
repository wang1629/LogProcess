
public class StepResult {
    
    public long startTime;
    public long endTime;
    public String counter;

    public boolean equals(StepResult step) {
        return  counter.equals(step.counter);
    }
    public long interval() {
        return endTime - startTime;
    }

    public String toString() {
        return counter + "(" + startTime + "~" + endTime + ")";
    }
}

