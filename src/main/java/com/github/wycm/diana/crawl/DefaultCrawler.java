package com.github.wycm.diana.crawl;

import com.github.wycm.diana.model.DianaPage;
import com.github.wycm.diana.model.DianaRequest;
import com.github.wycm.diana.model.PagingProcessor;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by wycm on 2019-11-14.
 */
public class DefaultCrawler extends AbstractCrawler{

    protected HttpClient httpClient = new DefaultHttpClient();

    @Override
    public DianaPage crawl(DianaRequest request) throws ExecutionException, InterruptedException {
        crawlBefore(request);
        DianaPage dianaPage = new DianaPage();
        dianaPage.setDianaRequest(request);
        dianaPage.setResponse(httpClient.execute(request.getRequest()));
        crawlAfter(dianaPage);
        if (isPagination(dianaPage)) {
            paginationProcess(dianaPage);
        }
        return dianaPage;
    }

    @Override
    public void close() {
        httpClient.close();
    }

    @Override
    public void crawlBefore(DianaRequest dianaRequest) {

    }

    @Override
    public void crawlAfter(DianaPage page) {

    }

    public void paginationProcess(DianaPage page) {
        List<DianaRequest> urls = pagingProcessor.process(page);
        urls.forEach(i ->{
            getTaskContext().getTaskExecutor().execute(() ->{
                getTaskContext().getDiana().processUrl(i, false);
            });
        });

    }
}
