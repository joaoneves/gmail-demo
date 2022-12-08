package com.joaoneves.demo.gmail.api.utils;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
    
    public String post(String url, List<NameValuePair> params) throws Exception {
    	HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
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
