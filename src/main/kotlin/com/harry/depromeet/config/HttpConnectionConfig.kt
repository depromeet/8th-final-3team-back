package com.harry.depromeet.config

import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate


@Configuration
class HttpConnectionConfig {

    @Bean
    fun getCustomRestTemplate(): RestTemplate {
        val httpRequestFactory = HttpComponentsClientHttpRequestFactory()
        httpRequestFactory.setConnectTimeout(2000)
        httpRequestFactory.setReadTimeout(3000)
        val httpClient: CloseableHttpClient? = HttpClientBuilder.create()
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(20)
                .build()
        httpClient?.let {
            httpRequestFactory.httpClient = httpClient
        }
        return RestTemplate(httpRequestFactory)
    }
}