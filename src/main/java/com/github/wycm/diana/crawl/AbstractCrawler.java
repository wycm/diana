package com.github.wycm.diana.crawl;

import com.github.wycm.diana.model.PagingProcessor;
import com.github.wycm.diana.model.TaskContext;

/**
 * Created by wycm on 2019-11-14.
 */
public abstract class AbstractCrawler implements Crawler{
    private TaskContext taskContext;

    protected PagingProcessor pagingProcessor;


    public TaskContext getTaskContext() {
        return taskContext;
    }

    public void setTaskContext(TaskContext taskContext) {
        this.taskContext = taskContext;
    }


    public PagingProcessor getPagingProcessor() {
        return pagingProcessor;
    }

    public void setPagingProcessor(PagingProcessor pagingProcessor) {
        this.pagingProcessor = pagingProcessor;
    }
}
