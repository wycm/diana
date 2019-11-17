package com.github.wycm.diana.example;

import com.github.wycm.diana.Diana;
import com.github.wycm.diana.model.DianaRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.stream.Collectors;

/**
 * Created by wycm on 2019-11-14.
 */
public class HupuCrawler {
    public static void main(String[] args) {
        Diana.getInstance()
                .addUrl("https://bbs.hupu.com/vote")
                .isPagination(true)
                //设置分页处理器
                .setPagingProcessor(dianaPage -> Jsoup.parse(dianaPage.getResponse()).select("a.truetit")
                        .stream()
                        .map(i -> {
                            String detailUrl = "https://bbs.hupu.com/" + i.attr("href");
                            return DianaRequest.createRequest(detailUrl, "hupu-detail-page");
                        })
                        .collect(Collectors.toList()))
                //解析【hupu-detail】页面
                .setExtractor(page ->{
                    if ("hupu-detail-page".equals(page.getDianaRequest().getPageType())) {
                        Document document = Jsoup.parse(page.getResponse());
                        page.getExtractResult().put("title", document.title());
                        page.getExtractResult().put("content", document.select("div.quote-content").text());
                    }
                })
                .run();
    }
}
