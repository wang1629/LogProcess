
public class TailfHandler implements ShellOutputHandler  {
    private LogStream<Entry> logStream;

    public TailfHandler(LogStream<Entry> logStream) {
        this.logStream = logStream;
    }
    public void handle(String line) {
        Entry entry = new Entry(line);
        logStream.receiveNew(entry);
    }
}
