package com.sk.interview.c3.model.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Component
public class WebClientConfig {

	@Bean
	public WebClient getWebClient() {

		var httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(5000))
						.addHandlerLast(new WriteTimeoutHandler(5000)));

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		ExchangeStrategies strategies = ExchangeStrategies.builder().codecs(clientDefaultCodecsConfigurer -> {
			clientDefaultCodecsConfigurer.defaultCodecs()
					.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
			clientDefaultCodecsConfigurer.defaultCodecs()
					.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));

		}).build();

		WebClient webClient = WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.exchangeStrategies(strategies).build();

		return webClient;
	}

}
