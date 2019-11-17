package com.github.wycm.diana.model;

import com.github.wycm.diana.utils.DianaConstants;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;

/**
 * Created by wycm on 2019-11-14.
 */
public class DianaRequest {
    private Request request;

    private String pageType = "default-page";

    private boolean pagination = false;


    private DianaRequest() {

    }




    public static DianaRequest createRequest(String url) {
        DianaRequest dianaRequest = new DianaRequest();
        Request request = new RequestBuilder()
                .setUrl(url)
                .addHeader("user-agent", DianaConstants.DEFAULT_USER_AGENT)
                .build();
        dianaRequest.request = request;
        return dianaRequest;
    }

    public static DianaRequest createRequest(String url, String pageType) {
        DianaRequest request = createRequest(url);
        request.setPageType(pageType);
        return request;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public boolean isPagination() {
        return pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }
}
