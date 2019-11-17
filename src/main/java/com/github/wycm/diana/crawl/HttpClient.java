package com.github.wycm.diana.crawl;

import org.asynchttpclient.Request;

import java.util.concurrent.ExecutionException;

/**
 * Created by wycm on 2019-11-14.
 */
public interface HttpClient {
    String get(String url) throws ExecutionException, InterruptedException;

    String execute(Request request) throws ExecutionException, InterruptedException;

    void close();
}
