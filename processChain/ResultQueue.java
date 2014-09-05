
import java.util.Timer;
import java.util.TimerTask;
import java.util.LinkedList;

public class ResultQueue {

    private LinkedList<StepResult> steps = new LinkedList<StepResult>();
    private int watchDog = 0;
    private String keyString;

    public ResultQueue(String keyString ) {
        setKey(keyString);
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {
                System.out.println("timer work!");
                if(watchDog >= 2) {
                    addNewStepResult(null);
                    this.cancel();
                }
                watchDog++;
            }
        }, 1*1000, 1*1000);
    }


    public void setKey(String keyString) {
        this.keyString = keyString;
    }

    public static void appendToFile(String line) {
        System.out.println("appendToFile");
        Write.write("result_file.txt", line);
    }

    public void addNewStepResult(StepResult stepResult) {
        if(stepResult == null) {
            reduce();
            return;
        }
        watchDog = 0;
        steps.add(stepResult);
    }

    private void reduce() {
        String json = "{\"keyString\":\"" + keyString + "\" , \"CounterInterval\":[ ";
        String end =  "]},";
        String valueArray = "";
        for(int i=0; i<steps.size(); i++) {
            StepResult s = steps.get(i);
            valueArray += "{\"Counter\":\"" + s.counter + "\", \"Interval\":" + s.interval() + "}";
            if(i<steps.size()-1) {
                valueArray += ",";
            }
        }
        String line = json + valueArray + end;

        appendToFile(line);

    }

}

