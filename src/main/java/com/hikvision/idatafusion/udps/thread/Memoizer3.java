package com.hikvision.idatafusion.udps.thread;

import java.util.Map;
import java.util.concurrent.*;

public class Memoizer3 implements Computable{

    private final Map<Integer, Future<Integer>> cache =  new ConcurrentHashMap<Integer, Future<Integer>>();

    @Override
    public Integer compute(int arg) throws Throwable {

        int output = 0;

        while(true){
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

                f = cache.putIfAbsent(arg, ft);
                if(f == null){ // 需要重新计算，一开始的hashmap中没有缓存对应key缓存的map值
                    f = ft;
                    ft.run();
                }
            }
            try {
                output =  f.get();
            } catch (ExecutionException e) {
                throw new Throwable(e.getCause());
            } catch (CancellationException e){
                cache.remove(arg,f);
            }
            return output;
        }

    }
}
