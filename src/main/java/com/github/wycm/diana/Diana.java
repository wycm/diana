package com.github.wycm.diana;

import com.github.wycm.diana.crawl.AbstractCrawler;
import com.github.wycm.diana.crawl.DefaultCrawler;
import com.github.wycm.diana.extractor.Extractor;
import com.github.wycm.diana.extractor.TitleExtractor;
import com.github.wycm.diana.model.*;
import com.github.wycm.diana.store.ConsoleStore;
import com.github.wycm.diana.store.Store;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wycm on 2019-11-14.
 */
public class Diana {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private List<DianaRequest> requests = new ArrayList<>();

    private int threads = 1;

    private ThreadPoolExecutor executorService;

    private AbstractCrawler crawler = new DefaultCrawler();

    private Extractor extractor = new TitleExtractor();

    private Store store = new ConsoleStore();

    private TaskContext taskContext;

    private PagingProcessor pagingProcessor;

    private boolean isPagination = false;

    public static Diana getInstance() {
        return new Diana();
    }

    public Diana setThreas(int threads) {
        this.threads = threads;
        return this;
    }

    public Diana setCrawler(AbstractCrawler crawler) {
        this.crawler = crawler;
        return this;
    }

    public Diana setExtractor(Extractor extractor) {
        this.extractor = extractor;
        return this;
    }

    public Diana setJsoupPaging(String urlsCssQuery) {
        pagingProcessor = new JsoupPagingProcessor(urlsCssQuery);
        return this;
    }

    public Diana setPagingProcessor(PagingProcessor pagingProcessor) {
        this.pagingProcessor = pagingProcessor;
        return this;
    }

    public Diana setStore(Store store) {
        this.store = store;
        return this;
    }

    public Diana isPagination(boolean isPagination) {
        this.isPagination = isPagination;
        return this;
    }

    public Diana addUrl(String url) {
        requests.add(DianaRequest.createRequest(url));
        return this;
    }

    public Diana addUrl(String url, String pageType) {
        requests.add(DianaRequest.createRequest(url, pageType));
        return this;
    }

    public void init() {
        executorService = new ThreadPoolExecutor(threads, threads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        taskContext = new TaskContext(this, executorService);
        crawler.setTaskContext(taskContext);
        if (CollectionUtils.isEmpty(requests)) {
            throw new RuntimeException("Parameter urls cannot be empty");
        }
        if (pagingProcessor != null) {
            crawler.setPagingProcessor(pagingProcessor);
        }
    }

    public void run() {
        init();
        requests.forEach(i -> {
                executorService.execute(() -> {
                    processUrl(i, isPagination);
                });

        });
        while (executorService.getQueue().size() > 0
                || executorService.getActiveCount() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        executorService.shutdown();
        crawler.close();
    }

    public void processUrl(DianaRequest dianaRequest, boolean isPagination) {
        try {
            dianaRequest.setPagination(isPagination);
            DianaPage dianaPage = crawler.crawl(dianaRequest);
            extractor.extract(dianaPage);
            store.store(dianaPage);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
