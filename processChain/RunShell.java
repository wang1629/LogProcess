
import java.io.*;

public class RunShell {

    public void exec(String command, ShellOutputHandler handler) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader inStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader input = new BufferedReader(inStreamReader);
            
            String line;
            while( (line = input.readLine()) != null) {
                handler.handle(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
