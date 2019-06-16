package tech.ftchekure.documentslostandfound.service.dtos;

import lombok.Data;

import java.util.Collection;

@Data
public class ItemTypeDto {

    private String name;

    private Collection<Long> itemAttributes;
}
