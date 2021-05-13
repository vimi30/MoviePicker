package com.example.moviepicker;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    //Executors are used to perform task in the background thread
    //it follows the singleton pattern

    private static AppExecutors appExecutorsInstance;

    public static AppExecutors getInstance(){

        if(appExecutorsInstance==null){
             appExecutorsInstance = new AppExecutors();
        }

        return appExecutorsInstance;

    }

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3); // here 3 says that we are going to use 3 background threads

    public ScheduledExecutorService networkIO(){

        return executorService;
    }


}
