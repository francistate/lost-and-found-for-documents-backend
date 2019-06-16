package tech.ftchekure.documentslostandfound.service.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class FoundItemDto {

    private Long itemTypeId;

    private Long stationId;

    private Map<Long, String> attributeValues;

    private String description;

}
