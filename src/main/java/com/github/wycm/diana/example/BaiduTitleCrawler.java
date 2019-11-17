package com.github.wycm.diana.example;

import com.github.wycm.diana.Diana;

/**
 * Created by wycm on 2019-11-14.
 */
public class BaiduTitleCrawler {
    public static void main(String[] args) {
        Diana.getInstance().addUrl("https://www.baidu.com").run();
    }
}
