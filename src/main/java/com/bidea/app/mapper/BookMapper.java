package com.bidea.app.mapper;

import com.bidea.app.model.dto.BookRequestDto;
import com.bidea.app.model.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.BookRequest;

@Mapper(componentModel = "spring")
public interface BookMapper {

  @Mapping(target = "userId", source = "id")
  BookRequestDto toRequestDto(BookRequest bookRequest);

  BookEntity toEntity(BookRequest bookRequest);

}
