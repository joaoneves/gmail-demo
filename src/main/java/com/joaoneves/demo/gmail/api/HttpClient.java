package com.joaoneves.demo.gmail.api;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClient {
	
	private CloseableHttpClient httpclient;

    public HttpClient() {
        httpclient = HttpClients.createDefault();
    }

    public String get(String url, String accessToken) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", "Bearer " + accessToken);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        validateStatus(response.getStatusLine().getStatusCode());
        return EntityUtils.toString(response.getEntity());
    }
    
    public String post(String url, String payload) throws Exception {
    	HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(payload);
        httpPost.setEntity(entity);
        httpPost.setHeader("Host", "accounts.google.com");
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

        CloseableHttpResponse response = httpclient.execute(httpPost);
        validateStatus(response.getStatusLine().getStatusCode());
        return EntityUtils.toString(response.getEntity());
    }

    private void validateStatus(Integer statusCode) {
        if (!statusCode.toString().startsWith("2")) {
            throw new RuntimeException("Request error code: " + statusCode);
        }
    }

}