package dolinh.mblog.utils;

import java.util.function.Supplier;

public class RetryUtil {
    public static<T> T execute(int maxAttempts, long delayMs, Supplier<T> action){
        int attemp = 0;
        while(true){
            try{
                return action.get();
            }
            catch (Exception e){
                attemp++;
                if(attemp > maxAttempts){
                    throw new RuntimeException("Max retry reached", e);
                }
                try {
                    Thread.sleep(delayMs);
                }
                catch (InterruptedException ignored){}
            }
        }
    }
}