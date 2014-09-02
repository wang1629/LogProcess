
import java.util.concurrent.*;

public class LogStream<T> {

    private BlockingQueue<T> queue = new ArrayBlockingQueue<T>(1024);

    public void receiveNew(T t) {
        try {
            queue.put(t);
        } catch (Exception ex) {
            System.out.println("Exception on receiveNew. " + ex.getMessage());
        }
    }

    public T next() {
        try {
            return queue.take();
        } catch (Exception ex) {
            System.out.println("Exception on receiveNew. " + ex.getMessage());
        }
        return null; // never here
    }

}
