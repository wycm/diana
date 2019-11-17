diana
====
diana是一个基于Java的垂直爬虫框架，目的是通过封装垂直爬虫常用流程，来简化垂直爬虫开发

## 需要
1. jdk 1.8

## Example
1. 爬取百度首页的标题(详细参考：com.github.wycm.diana.example.BaiduTitleCrawler)
```
public class BaiduTitleCrawler {
    public static void main(String[] args) {
        Diana.getInstance().addUrl("https://www.baidu.com").run();
    }
}
```

2. 爬取虎扑网站帖子标题和内容(详细参考：com.github.wycm.diana.example.HupuCrawler)
```
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
                //解析【hupu-detail-page】页面
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
```

