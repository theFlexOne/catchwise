package com.flexone.catchwise.mapper;

import com.flexone.catchwise.domain.Fish;
import com.flexone.catchwise.dto.LakeFishResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FishToLakeFishResponseMapper {
    FishToLakeFishResponseMapper INSTANCE = Mappers.getMapper(FishToLakeFishResponseMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    LakeFishResponse fishToLakeFishResponse(Fish fish);

    @AfterMapping
    default void addUrl(@MappingTarget LakeFishResponse lakeFishResponse, Fish fish) {
        String baseUrl = "http://localhost:8080/api/v1/fish/";
        String fishUrl = baseUrl + fish.getId();
        lakeFishResponse.setUrl(fishUrl);
    }
}