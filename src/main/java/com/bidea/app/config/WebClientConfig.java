package com.bidea.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class WebClientConfig {

  @Value("${aws.external-service.url}")
  private String uri;

  @Bean
  public WebClient webClient() {
    log.info("Configurando WebClient para servicio externo endpoint: {}", uri);
    return WebClient.builder().baseUrl(uri)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
  }
}
