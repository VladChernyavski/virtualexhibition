package by.cniitu.virtualexhibition.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MailThreadExecutorUtil {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8,
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public static void execute(Runnable runnable){
        executor.execute(runnable);
    }
}
