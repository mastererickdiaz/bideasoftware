package com.bidea.app.service;

import static com.bidea.app.util.TestUtil.jsonStringToObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.bidea.app.mapper.BookMapper;
import com.bidea.app.model.dto.BookResponseDto;
import com.bidea.app.repository.BookRepository;
import com.bidea.app.service.impl.DiscountServiceImpl;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.BookRequest;
import org.openapitools.model.BookResponse;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

  @InjectMocks
  private DiscountServiceImpl service;

  @Mock
  private ExternalService externalService;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookMapper bookMapper;

  BookRequest request;
  BookResponseDto response;

  @BeforeEach
  void setUp() throws IOException {
    request = jsonStringToObject("service/request.json", BookRequest.class);
    response = jsonStringToObject("service/response.json", BookResponseDto.class);
  }

  @Test
  void processTest() {
    when(externalService.processDiscountCode(bookMapper.toRequestDto(request)))
        .thenReturn(Mono.just(response));

    Mono<BookResponse> mono = service.process(request);

    assertEquals(new BigDecimal(200), mono.block().getCode());
    verify(externalService, times(1)).processDiscountCode(bookMapper.toRequestDto(request));
  }


}
