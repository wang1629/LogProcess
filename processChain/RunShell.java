
import java.io.*;

public class RunShell {

    public void exec(String command, ShellOutputHandler handler) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader inStreamReader = new InputStreamReader(process.getInputStream());
            InputStreamReader errStreamReader = new InputStreamReader(process.getErrorStream());
            BufferedReader input = new BufferedReader(inStreamReader);
            final BufferedReader error = new BufferedReader(errStreamReader);

            (new Thread() {
                public void run() { 
                    try {
                        String errLine = "";
                        while( (errLine = error.readLine()) != null) {
                            System.err.println(errLine);
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }).start();

            String line;
            while( (line = input.readLine()) != null) {
                handler.handle(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
