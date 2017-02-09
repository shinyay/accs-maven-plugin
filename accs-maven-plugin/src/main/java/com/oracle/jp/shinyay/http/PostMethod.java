package com.oracle.jp.shinyay.http;

import com.oracle.jp.shinyay.util.ACCSConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostMethod {
    private PostMethod() {
    }

    public static String HttpPostMethod(String url, BasicNameValuePair[] headers, HttpEntity body, Credentials credUser) throws Exception {
        String httpResult;

        HttpPost httpPost = new HttpPost(url);
        for (BasicNameValuePair header : headers) {
            httpPost.addHeader(header.getName(), header.getValue());
        }

        httpPost.setEntity(body);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(new HttpHost(ACCSConstants.REST_API_DOMAIN_EMEA, 443)), credUser);

        RequestConfig requestConfig = RequestConfig.custom().build();

        Arrays.stream(httpPost.getAllHeaders()).forEach(System.out::println);
        System.out.println(EntityUtils.toString(httpPost.getEntity()));

        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create()
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setDefaultRequestConfig(requestConfig).build();
                CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        ) {
            int httpStatus = httpResponse.getStatusLine().getStatusCode();

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String[] results = EntityUtils.toString(entity).split(ACCSConstants.LINE_SEPARATOR_LINUX);
                httpResult = Arrays.stream(results).map(s -> s + ACCSConstants.LINE_SEPARATOR).collect(Collectors.joining());
            } else {
                httpResult = httpResponse.getStatusLine() + ACCSConstants.LINE_SEPARATOR;
            }
        }
        return httpResult.toString();
    }
}
