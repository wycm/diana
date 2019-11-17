package com.github.wycm.diana.model;

import org.jsoup.Jsoup;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wycm on 2019-11-15.
 */
public class JsoupPagingProcessor implements PagingProcessor{

    private String cssQuery;

    public JsoupPagingProcessor(String urlsCssQuery) {
        this.cssQuery = urlsCssQuery;
    }

    @Override
    public List<DianaRequest> process(DianaPage dianaPage) {
        return Jsoup.parse(dianaPage.getResponse()).select(cssQuery)
                .stream()
                .map(i -> {
                    String url = i.attr("href");
                    return DianaRequest.createRequest(url, "detail-page");
                })
                .collect(Collectors.toList());
    }
}
