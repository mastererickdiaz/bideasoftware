package com.bidea.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BookRequestDto {
  
  private String userId;
  private String houseId;
  private String discountCode;
}
