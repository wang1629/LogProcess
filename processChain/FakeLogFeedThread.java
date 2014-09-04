
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class FakeLogFeedThread extends FeedThread {

    String masterLogPath = "/home/wang/work/LPS/LogProcess/fakeServer/res/master.log";
    String sumWorkerLogPath = "/home/wang/work/LPS/LogProcess/fakeServer/res/sumWorker.log";
    String sqrtWorkerLogPath = "/home/wang/work/LPS/LogProcess/fakeServer/res/sqrtWorker.log";

    BufferedReader masterReader;
    BufferedReader sumReader;
    BufferedReader sqrtReader;

    public FakeLogFeedThread(LogStream<Entry> logStream) {
        super(logStream);
    }

    public void openLogFile() {
        File masterFile = new File(masterLogPath);
        File sumFile = new File(sumWorkerLogPath);
        File sqrtFile = new File(sqrtWorkerLogPath);
        try {
            masterReader = new BufferedReader(new FileReader(masterFile));
            sumReader = new BufferedReader(new FileReader(sumFile));
            sqrtReader = new BufferedReader(new FileReader(sqrtFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        openLogFile();
        try{
            String masterLogLine;
            String sumLogLine;
            String sqrtLogLine;

            while(true) {
                int end = 0;
                masterLogLine = masterReader.readLine();
                sumLogLine = sumReader.readLine();
                sqrtLogLine = sqrtReader.readLine();

                if(masterLogLine != null) {
                    Entry entry = new Entry();
                    entry.parseFrom(masterLogLine);
                    logStream.receiveNew(entry);
                } else {
                    end++; 
                }

                if(sumLogLine != null) {
                    Entry entry = new Entry();
                    entry.parseFrom(sumLogLine);
                    logStream.receiveNew(entry);
                } else {
                    end++; 
                }

                if(sqrtLogLine != null) {
                    Entry entry = new Entry();
                    entry.parseFrom(sqrtLogLine);
                    logStream.receiveNew(entry);
                } else {
                    end++; 
                }

                if(end == 3) {
                    break;
                }
            }
            
            masterReader.close();
            sumReader.close();
            sqrtReader.close();

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void mainAAA(String args[]) {
    }


}





