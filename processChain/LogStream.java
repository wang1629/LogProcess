
import java.util.concurrency.*;

public class LogStream<T> {

    private BlockingQueue<T> queue = new ArrayBlockingQueue<T>(4);

    public void receiveNew(T t) {
        try {
            queue.put(t);
        } catch (Exception ex) {
            System.out.println("Exception on receiveNew. " + e.getMessage());
        }
    }

    public T next() {
        try {
            return queue.take();
        } catch (Exception ex) {
            System.out.println("Exception on receiveNew. " + e.getMessage());
        }
    }

}
