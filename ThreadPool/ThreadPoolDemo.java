import threadpool_internals.FixedThreadPool;
import threadpool_internals.Task;

public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thr;
        FixedThreadPool tp = new FixedThreadPool();

        thr = new Thread(tp);
        thr.start();

        while (thr.isAlive()) {
            thr.join(1000);
            for (int i = 0; i < 7; i++) {
                tp.addTaskToPendingQueue(new Task(() -> {
                        try {
                            System.out.println("task: hello world");
                            Thread.sleep(5000);
                            System.out.println("");
                        } catch (InterruptedException e) {
                        }
                    }));
            }
        }
    }
}