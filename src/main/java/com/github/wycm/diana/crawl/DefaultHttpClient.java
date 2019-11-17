package com.github.wycm.diana.crawl;

import com.github.wycm.diana.utils.DianaConstants;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.asynchttpclient.*;
import org.asynchttpclient.cookie.CookieStore;
import org.asynchttpclient.cookie.ThreadSafeCookieStore;
import org.asynchttpclient.proxy.ProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DefaultHttpClient implements HttpClient{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private AsyncHttpClient asyncHttpClient;


    private int timeout = 15000;

    public DefaultHttpClient(){
        this(500, 2000);
    }

    public DefaultHttpClient(int maxConnectionsPerHost, int maxConnections){
        this(new ThreadSafeCookieStore(), maxConnectionsPerHost, maxConnections);
    }
    public DefaultHttpClient(CookieStore cookieStore, int maxConnectionsPerHost, int maxConnections){
        SslContext sslCtx = null;
        try {
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } catch (SSLException e) {
            e.printStackTrace();
        }
        DefaultAsyncHttpClientConfig.Builder builder = Dsl.config()
                .setConnectTimeout(timeout)
                .setHandshakeTimeout(timeout)
                .setReadTimeout(timeout)
                .setRequestTimeout(timeout)
                .setShutdownTimeout(timeout)
                .setSslSessionTimeout(timeout)
                .setPooledConnectionIdleTimeout(timeout)
                .setMaxConnectionsPerHost(maxConnectionsPerHost)
                .setMaxConnections(maxConnections)
                .setSslContext(sslCtx)
                .setMaxRedirects(3)
                .setFollowRedirect(true);
        if (cookieStore != null){
            builder.setCookieStore(cookieStore);
        }
        asyncHttpClient = new DefaultAsyncHttpClient(builder.build());
    }
    public String get(String url) throws ExecutionException, InterruptedException {
        Request request = new RequestBuilder()
                .addHeader("user-agent", DianaConstants.DEFAULT_USER_AGENT)
                .setUrl(url)
                .build();
        return execute(request);
    }

    public String get(String url, ProxyServer proxyServer) throws ExecutionException, InterruptedException {
        Request request = new RequestBuilder()
                .setUrl(url)
                .setProxyServer(proxyServer)
                .build();
        return execute(request);
    }

    public String execute(Request request) throws ExecutionException, InterruptedException {
        return executeRequest(request).getResponseBody();
    }

    @Override
    public void close() {
        try {
            this.asyncHttpClient.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Response getResponse(String url, ProxyServer proxyServer) throws ExecutionException, InterruptedException {
//        proxyServer = new ProxyServer.Builder("127.0.0.1", 8888).build();
        Request request = new RequestBuilder()
                .setUrl(url)
                .setProxyServer(proxyServer)
                .build();
        return executeRequest(request);
    }
    public Response getResponse(String url, ProxyServer proxyServer, String userAgent) throws ExecutionException, InterruptedException {
//        proxyServer = new ProxyServer.Builder("127.0.0.1", 8888).build();
        RequestBuilder builder = new RequestBuilder();
        builder.resetCookies();
        Request request = builder
                .setUrl(url)
                .setProxyServer(proxyServer)
                .setHeader("user-agent", userAgent)
                .build();
        return executeRequest(request);
    }
    public Response getResponse(String url, ProxyServer proxyServer, String userAgent, Map<String, String> headers) throws ExecutionException, InterruptedException {
//        proxyServer = new ProxyServer.Builder("127.0.0.1", 8888).build();
        RequestBuilder builder = new RequestBuilder();
        builder.resetCookies();
        builder.setUrl(url)
                .setProxyServer(proxyServer)
                .setHeader("user-agent", userAgent);
        headers.forEach(builder::setHeader);
        return executeRequest(builder.build());
    }

    public Response executeRequest(Request request) throws InterruptedException, ExecutionException {
        Response res = null;
        res = asyncHttpClient.executeRequest(request).get();
        return res;
    }
}
