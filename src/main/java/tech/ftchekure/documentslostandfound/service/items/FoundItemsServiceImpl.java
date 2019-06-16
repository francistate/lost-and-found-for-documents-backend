package tech.ftchekure.documentslostandfound.service.items;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.items.FoundItemsRepository;
import tech.ftchekure.documentslostandfound.daos.items.ItemAttributesRepository;
import tech.ftchekure.documentslostandfound.daos.items.ItemTypesRepository;
import tech.ftchekure.documentslostandfound.daos.items.ItemsRepository;
import tech.ftchekure.documentslostandfound.daos.user.StationRepository;
import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.entities.items.Item;
import tech.ftchekure.documentslostandfound.entities.items.ItemAttribute;
import tech.ftchekure.documentslostandfound.entities.items.ItemType;
import tech.ftchekure.documentslostandfound.entities.user.User;
import tech.ftchekure.documentslostandfound.service.dtos.FoundItemDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class FoundItemsServiceImpl implements FoundItemsService {

    private final FoundItemsRepository foundItemsRepository;

    private final ItemsRepository itemsRepository;

    private final ItemTypesRepository itemTypesRepository;

    private final ItemAttributesRepository itemAttributesRepository;

    private final StationRepository stationRepository;

    private final LostItemComparisonChecker lostItemComparisonChecker;

    public FoundItemsServiceImpl(FoundItemsRepository foundItemsRepository,
                                 ItemsRepository itemsRepository,
                                 ItemTypesRepository itemTypesRepository,
                                 ItemAttributesRepository itemAttributesRepository,
                                 StationRepository stationRepository,
                                 LostItemComparisonChecker lostItemComparisonChecker) {
        this.foundItemsRepository = foundItemsRepository;
        this.itemsRepository = itemsRepository;
        this.itemTypesRepository = itemTypesRepository;
        this.itemAttributesRepository = itemAttributesRepository;
        this.stationRepository = stationRepository;
        this.lostItemComparisonChecker = lostItemComparisonChecker;
    }

    @Override
    public Page<FoundItem> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<FoundItem>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return foundItemsRepository.findAll(pageRequest);
        else
            return foundItemsRepository.findAll(spec, pageRequest);

    }

    @Override
    public List<FoundItem> findAll() {
        return null;
    }

    @Override
    public FoundItem save(FoundItemDto foundItemDto) {

        val attributeValues = getAttributesAndValues(foundItemDto.getAttributeValues());

        val itemType = getItemType(foundItemDto.getItemTypeId());

        val station = stationRepository.findById(foundItemDto.getStationId()).get();

        val description = foundItemDto.getDescription();

        val item = Item.createItem(attributeValues, itemType, description);

        val persistedItem = itemsRepository.save(item);

        val foundItem = new FoundItem();
        foundItem.setItem(persistedItem);
        foundItem.setStation(station);
        //foundItem.setDescription(foundItemDto.getDescription());

        val persistedFoundItem = foundItemsRepository.save(foundItem);

        lostItemComparisonChecker.compare(persistedFoundItem);

        return persistedFoundItem;
    }

    @Override
    public FoundItem update(Long id, FoundItemDto foundItemDto) {
        val foundItem = findById(id);
        //foundItem.setDescription(foundItemDto.getDescription());

        val item = foundItem.getItem();
        item.setItemType(getItemType(foundItemDto.getItemTypeId()));
        item.setAttributeValues(getAttributesAndValues(foundItemDto.getAttributeValues()));
        item.setDescription(foundItemDto.getDescription());

        val updatedItem = itemsRepository.save(item);
        foundItem.setItem(updatedItem);
        return foundItemsRepository.save(foundItem);
    }

    private Map<ItemAttribute, String> getAttributesAndValues(Map<Long, String> attributeValues) {

        val attributeValues2 = new HashMap<ItemAttribute, String>();

        attributeValues.entrySet().stream().forEach(attributeValueEntry -> {
            val attribute = itemAttributesRepository.findById(attributeValueEntry.getKey()).get();
            attributeValues2.merge(attribute, attributeValueEntry.getValue(), (v1, v2) -> v1);
        });

        return attributeValues2;
    }

    private ItemType getItemType(Long itemTypeId) {
        return itemTypesRepository.findById(itemTypeId).get();
    }

    @Override
    public void delete(Long id) {
        foundItemsRepository.deleteById(id);
    }

    @Override
    public FoundItem findById(Long id) {
        return foundItemsRepository.findById(id).get();
    }


    public Collection<FoundItem> findByStationId(Long stationId) {

        return foundItemsRepository.findAllByStationId(stationId);

    }
}
