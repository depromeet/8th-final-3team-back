package com.harry.depromeet.config

import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.ssl.SSLContexts
import org.apache.http.ssl.TrustStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext


@Configuration
class HttpConnectionConfig {

    @Bean
    fun getCustomRestTemplate(): RestTemplate {
        val acceptingTrustStrategy = TrustStrategy { chain: Array<X509Certificate?>?, authType: String? -> true }
        val sslContext: SSLContext = SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build()

        val csf = SSLConnectionSocketFactory(sslContext)

        val httpRequestFactory = HttpComponentsClientHttpRequestFactory()
        httpRequestFactory.setConnectTimeout(2000)
        httpRequestFactory.setReadTimeout(3000)
        val httpClient: CloseableHttpClient? = HttpClientBuilder.create()
                .setSSLSocketFactory(csf)
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(20)
                .build()
        httpClient?.let {
            httpRequestFactory.httpClient = httpClient
        }
        return RestTemplate(httpRequestFactory)
    }
}