package tech.ftchekure.documentslostandfound.service.items;

import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.service.BaseEntityService;
import tech.ftchekure.documentslostandfound.service.dtos.FoundItemDto;

import java.util.Collection;

public interface FoundItemsService extends BaseEntityService<FoundItem, FoundItemDto, Long> {

    Collection<FoundItem> findByStationId(Long stationId);
}
