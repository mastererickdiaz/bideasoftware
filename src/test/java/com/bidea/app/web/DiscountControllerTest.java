package com.bidea.app.web;

import static com.bidea.app.util.TestUtil.jsonStringToObject;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.bidea.app.service.DiscountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.BookRequest;
import org.openapitools.model.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

@WebMvcTest(DiscountController.class)
public class DiscountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  DiscountService service;

  BookRequest request;
  BookResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    request = jsonStringToObject("controller/request.json", BookRequest.class);
    response = jsonStringToObject("controller/response.json", BookResponse.class);
  }

  @Test
  public void test_post_process() throws Exception {
    given(service.process(any(BookRequest.class))).willReturn(Mono.just(response));
    mockMvc
        .perform(post("/book").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.code", is(200)));

    verify(service, times(1)).process(any(BookRequest.class));
  }

}
