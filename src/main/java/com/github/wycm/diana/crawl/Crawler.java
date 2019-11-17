package com.github.wycm.diana.crawl;

import com.github.wycm.diana.model.DianaPage;
import com.github.wycm.diana.model.DianaRequest;

import java.util.concurrent.ExecutionException;

/**
 * Created by wycm on 2019-11-14.
 */
public interface Crawler {

    DianaPage crawl(DianaRequest request) throws ExecutionException, InterruptedException;

    void close();

    void crawlBefore(DianaRequest dianaRequest);

    void crawlAfter(DianaPage page);

    /**
     * 分页处理
     * @param page
     */
    default void paginationProcess(DianaPage page) {

    }

    default boolean isPagination(DianaPage page) {
        return page.getDianaRequest().isPagination();
    }

}
