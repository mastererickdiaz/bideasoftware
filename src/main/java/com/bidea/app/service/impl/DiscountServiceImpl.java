package com.bidea.app.service.impl;

import com.bidea.app.exception.ApiException;
import com.bidea.app.mapper.BookMapper;
import com.bidea.app.model.dto.BookResponseDto;
import com.bidea.app.model.entity.BookEntity;
import com.bidea.app.repository.BookRepository;
import com.bidea.app.service.DiscountService;
import com.bidea.app.service.ExternalService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.BookRequest;
import org.openapitools.model.BookResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

  private final ExternalService externalService;

  private final BookRepository bookRepository;

  public final BookMapper bookMapper;

  @Override
  public Mono<BookResponse> process(BookRequest bookRequest) {
    log.debug("Init - process: {} ", bookRequest.getDiscountCode());
    return externalService.processDiscountCode(bookMapper.toRequestDto(bookRequest))
        .flatMap(p -> processBook(p, bookRequest));
  }

  private Mono<BookResponse> processBook(BookResponseDto bookRequestDto, BookRequest bookRequest) {
    if (bookRequestDto.getDiscountCode() != null) {
      return Mono.just(saveBook(bookRequest));
    }
    log.error("Error discountCode invalid, {}", bookRequestDto.getDiscountCode());
    return Mono.error(new ApiException("Error processBook"));
  }

  private BookResponse saveBook(BookRequest bookRequest) {
    log.debug("Init - saveBook: {} ", bookRequest.getId());
    BookEntity bookEntity = bookMapper.toEntity(bookRequest);
    bookRepository.save(bookEntity);

    BookResponse response = new BookResponse();
    response.setCode(new BigDecimal(200));
    response.setMessage("Book Acepted");
    return response;
  }

}
