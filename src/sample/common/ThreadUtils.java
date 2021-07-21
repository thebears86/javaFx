package sample.common;

public class ThreadUtils {

    public static Thread getThreadByName(String threadName){
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) return t;
        }
        return null;
    }

}
