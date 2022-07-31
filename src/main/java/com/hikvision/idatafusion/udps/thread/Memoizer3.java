package com.hikvision.idatafusion.udps.thread;

import java.util.Map;
import java.util.concurrent.*;

public class Memoizer3 implements Computable{

    private final Map<Integer, Future<Integer>> cache =  new ConcurrentHashMap<Integer, Future<Integer>>();

    @Override
    public Integer compute(int arg) throws InterruptedException {

        int output = 0;

        Future<Integer> f = cache.get(arg);
        if(f == null){
            // 则创建缓存，并且放入concurrentHashmap中
            Callable<Integer> callable = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("task is running " + arg);
                    return arg * arg + arg + arg;
                }
            };

            FutureTask<Integer> ft = new FutureTask<>(callable);

            f = ft;
            cache.put(arg,ft);
            ft.run();
        }
        try {
           output =  f.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return output;
    }
}
