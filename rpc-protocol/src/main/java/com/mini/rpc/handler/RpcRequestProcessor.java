package com.mini.rpc.handler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc请求处理器，用于将具体的请求处理任务交到业务线程池中执行
 * @date 2022/6/20 1:50 下午
 */
public class RpcRequestProcessor {

    private static volatile ThreadPoolExecutor threadPoolExecutor;

    public static void submitReqHandlerTask(Runnable task){
        // 双重检查的单例模式实现
        if(null == threadPoolExecutor){
            // 设置线程池创建的线程的名字
            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("RpcThread-Pool-%d").build();

            synchronized(RpcRequestProcessor.class){
                if(null == threadPoolExecutor){
                    threadPoolExecutor = new ThreadPoolExecutor(
                            10,
                            10,
                            60L,
                            TimeUnit.SECONDS,
                            new ArrayBlockingQueue<>(10000),
                            threadFactory,
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        // 使用创建的线程池提交任务
        threadPoolExecutor.submit(task);
    }

}
