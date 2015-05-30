package com.sdust.im.server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 线程池 减少线程的创建，增加重用性
 *
 */
public class ThreadPool {

    private static Executor service;
    private static ThreadPool pool;
    public static ThreadPool getThreadPool(){
    	return pool;
    }
    private ThreadPool(){
    	service = Executors.newCachedThreadPool() ;
    }
    public void  execute(Runnable thread){
    	service.execute(thread);
    }


}
