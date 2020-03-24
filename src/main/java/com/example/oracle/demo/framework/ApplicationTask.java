package com.example.oracle.demo.framework;


import com.example.oracle.demo.framework.context.ApplicationContext;

public interface ApplicationTask extends Skipable {

    boolean perform(ApplicationContext context) throws Exception;

    boolean hasNext();

    void registerNextTask(ApplicationTask nextTask);

    ApplicationTask next();

    //void initLogger(String applicationTaskId, String applicationId);
}
