package com.jie.bookshare.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.*;

/*
 * @Author Haojie
 * @Description 请求工具类
 **/

public class HttpClient {
    private String url;
    private Map<String, String> param;
    private int statusCode;
    private String content;
    private String xmlParam;
    private boolean isHttps;

    public boolean isHttps() {
        return this.isHttps;
    }

    public void setHttps(boolean isHttps) {
        this.isHttps = isHttps;
    }

    public String getXmlParam() {
        return this.xmlParam;
    }

    public void setXmlParam(String xmlParam) {
        this.xmlParam = xmlParam;
    }

    public HttpClient(String url, Map<String, String> param) {
        this.url = url;
        this.param = param;
    }

    public HttpClient(String url) {
        this.url = url;
    }

    public void setParameter(Map<String, String> map) {
        this.param = map;
    }

    public void addParameter(String key, String value) {
        if (this.param == null) {
            this.param = new HashMap();
        }

        this.param.put(key, value);
    }

    public void post() throws ClientProtocolException, IOException {
        HttpPost http = new HttpPost(this.url);
        this.setEntity(http);
        this.execute(http);
    }

    public void put() throws ClientProtocolException, IOException {
        HttpPut http = new HttpPut(this.url);
        this.setEntity(http);
        this.execute(http);
    }

    public void get() throws ClientProtocolException, IOException {
        if (this.param != null) {
            StringBuilder url = new StringBuilder(this.url);
            boolean isFirst = true;

            String key;
            for(Iterator var3 = this.param.keySet().iterator(); var3.hasNext(); url.append(key).append("=").append((String)this.param.get(key))) {
                key = (String)var3.next();
                if (isFirst) {
                    url.append("?");
                    isFirst = false;
                } else {
                    url.append("&");
                }
            }

            this.url = url.toString();
        }

        HttpGet http = new HttpGet(this.url);
        this.execute(http);
    }

    private void setEntity(HttpEntityEnclosingRequestBase http) {
        if (this.param != null) {
            List<NameValuePair> nvps = new LinkedList();
            Iterator var3 = this.param.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                nvps.add(new BasicNameValuePair(key, (String)this.param.get(key)));
            }

            http.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        }

        if (this.xmlParam != null) {
            http.setEntity(new StringEntity(this.xmlParam, Consts.UTF_8));
        }

    }

    private void execute(HttpUriRequest http) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = null;

        try {
            if (this.isHttps) {
                SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, new TrustStrategy() {
                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return true;
                    }
                }).build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
                httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            } else {
                httpClient = HttpClients.createDefault();
            }

            CloseableHttpResponse response = httpClient.execute(http);

            try {
                if (response != null) {
                    if (response.getStatusLine() != null) {
                        this.statusCode = response.getStatusLine().getStatusCode();
                    }

                    HttpEntity entity = response.getEntity();
                    this.content = EntityUtils.toString(entity, Consts.UTF_8);
                }
            } finally {
                response.close();
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            httpClient.close();
        }

    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getContent() throws ParseException, IOException {
        return this.content;
    }
}

