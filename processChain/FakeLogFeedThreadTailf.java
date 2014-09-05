
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class FakeLogFeedThreadTailf extends FeedThread {

    String filePath = "";

    public FakeLogFeedThreadTailf(LogStream<Entry> logStream, String filePath) {
        super(logStream);
        this.filePath = filePath;
    }

    public void run() {
        String command = "tail -f " + filePath;
        RunShell runShell = new RunShell();
        TailfHandler tailfHandler = new TailfHandler(logStream);
        runShell.exec(command, tailfHandler);
    }
}





