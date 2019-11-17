package com.github.wycm.diana.extractor;

import com.github.wycm.diana.model.DianaPage;
import org.jsoup.Jsoup;

/**
 * Title extractor
 */
public class TitleExtractor implements Extractor{

    @Override
    public void extract(DianaPage page) {
        String title = Jsoup.parse(page.getResponse()).title();
        page.getExtractResult().put("title", title);
    }
}
