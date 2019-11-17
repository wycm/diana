package com.github.wycm.diana.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wycm on 2019-11-14.
 */
public class DianaPage {
    private String url;

    private int statusCode;

    private String response;

    private DianaRequest dianaRequest;

    private Map<Object, Object> extractResult = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public DianaRequest getDianaRequest() {
        return dianaRequest;
    }

    public void setDianaRequest(DianaRequest dianaRequest) {
        this.dianaRequest = dianaRequest;
    }

    public Map<Object, Object> getExtractResult() {
        return extractResult;
    }

    public void setExtractResult(Map<Object, Object> extractResult) {
        this.extractResult = extractResult;
    }
}
