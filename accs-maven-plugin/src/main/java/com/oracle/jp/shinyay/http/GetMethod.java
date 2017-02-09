package com.oracle.jp.shinyay.http;

import com.oracle.jp.shinyay.util.ACCSConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetMethod {
    private GetMethod() {
    }

    public static String HttpGetMethod(String url, BasicNameValuePair[] headers, StringEntity body, Credentials credUser) throws Exception {
        String httpResult;

        HttpGet httpGet = new HttpGet(url);
        for (BasicNameValuePair header : headers) {
            httpGet.addHeader(header.getName(), header.getValue());
        }

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(new HttpHost(ACCSConstants.REST_API_DOMAIN_EMEA, 443)), credUser);

        RequestConfig requestConfig = RequestConfig.custom().build();

        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create()
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setDefaultRequestConfig(requestConfig).build();
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
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
