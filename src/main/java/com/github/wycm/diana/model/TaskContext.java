package com.github.wycm.diana.model;

import com.github.wycm.diana.Diana;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wycm on 2019-11-14.
 */
public class TaskContext {
    private ThreadPoolExecutor taskExecutor;

    private Map<String, String> parameterMap = new HashMap<>();

    private Diana diana;

    public TaskContext(Diana diana, ThreadPoolExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
        this.diana = diana;
    }

    public ThreadPoolExecutor getTaskExecutor() {
        return taskExecutor;
    }


    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public Diana getDiana() {
        return diana;
    }
}
