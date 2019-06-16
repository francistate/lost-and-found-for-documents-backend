package tech.ftchekure.documentslostandfound.service.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class LostItemDto {

    private Map<Long, String> attributeValues;

    private Long itemTypeId;

    private String email;

    private String description;


}
