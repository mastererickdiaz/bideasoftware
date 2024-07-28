package com.bidea.app.web;

import com.bidea.app.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BookApi;
import org.openapitools.model.BookRequest;
import org.openapitools.model.BookResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DiscountController implements BookApi {

  private final DiscountService discountService;

  @Override
  public ResponseEntity<BookResponse> bookPost(BookRequest bookRequest) {
    return ResponseEntity.status(HttpStatus.OK).body(discountService.process(bookRequest).block());
  }

}
