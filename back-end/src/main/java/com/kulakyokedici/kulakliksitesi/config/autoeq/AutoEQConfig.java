package com.kulakyokedici.kulakliksitesi.config.autoeq;

import java.net.http.HttpClient;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;


@Configuration
public class AutoEQConfig
{
	
	@Value("${autoeq.service.url}")
	private String autoeqServiceUrl;
	
	@Bean
	public RestClient autoeqRestClient()
	{
		// JDK HttpClient'ı HTTP/1.1'e zorla — uvicorn HTTP/2 upgrade'ini
		// desteklemiyor, default davranışta body kayboluyor
		HttpClient jdkClient = HttpClient
				.newBuilder()
				.version(HttpClient.Version.HTTP_1_1)
				.connectTimeout(Duration.ofSeconds(3))
				.build();
		
		JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(jdkClient);
		factory.setReadTimeout(Duration.ofSeconds(15));
		
		return RestClient
				.builder()
				.baseUrl(autoeqServiceUrl)
				.requestFactory(factory)
				.build();
	}
}
