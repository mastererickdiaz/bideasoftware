package com.bidea.app.service.impl;

import com.bidea.app.exception.BookNotFoundException;
import com.bidea.app.model.dto.BookRequestDto;
import com.bidea.app.model.dto.BookResponseDto;
import com.bidea.app.service.ExternalService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalServiceImpl implements ExternalService {

  private final WebClient webClient;

  @Override
  @CircuitBreaker(name = "backendExternal", fallbackMethod = "fallback")
  @Retry(name = "backendExternal")
  public Mono<BookResponseDto> processDiscountCode(BookRequestDto bookRequestDto) {
    log.info("Consultando servicio externo: {}", bookRequestDto.getUserId());
    return webClient.post().body(Mono.just(bookRequestDto), BookRequestDto.class)
        .exchangeToMono(this::handleResponse);
  }

  @SuppressWarnings("unused")
  private Mono<String> fallback(CallNotPermittedException e) {
    return Mono.just("Handled the exception when the CircuitBreaker is open");
  }

  private Mono<BookResponseDto> handleResponse(ClientResponse response) {
    log.info("statusCode: {}", response.statusCode().value());
    if (response.statusCode().is2xxSuccessful()) {
      return response.bodyToMono(BookResponseDto.class);
    } else if (response.statusCode().is4xxClientError()) {
      // Handle client errors (e.g., 404 Not Found)
      return Mono.error(new BookNotFoundException("Book not found"));
    } else if (response.statusCode().is5xxServerError()) {
      // Handle server errors (e.g., 500 Internal Server Error)
      return Mono.error(new RuntimeException("Server error"));
    } else {
      // Handle other status codes as needed
      return Mono.error(new RuntimeException("Unexpected error"));
    }
  }
}
