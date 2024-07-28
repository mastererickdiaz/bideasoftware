package com.bidea.app.service;

import org.openapitools.model.BookRequest;
import org.openapitools.model.BookResponse;
import reactor.core.publisher.Mono;

public interface DiscountService {
  public Mono<BookResponse> process(BookRequest bookRequest);
}
