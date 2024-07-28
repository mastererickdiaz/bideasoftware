package com.bidea.app.service;

import com.bidea.app.model.dto.BookRequestDto;
import com.bidea.app.model.dto.BookResponseDto;
import reactor.core.publisher.Mono;

public interface ExternalService {
  Mono<BookResponseDto> processDiscountCode(BookRequestDto bookRequestDto);
}
