package tech.ftchekure.documentslostandfound.service.search;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.items.FoundItemsRepository;
import tech.ftchekure.documentslostandfound.daos.items.ItemAttributeValue;
import tech.ftchekure.documentslostandfound.daos.items.ItemAttributeValuesRepository;
import tech.ftchekure.documentslostandfound.daos.items.LostItemsRepository;
import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.entities.items.LostItem;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Service
@Slf4j
public class SearchServiceHandlerImpl implements SearchServiceHandler {

    private final ItemAttributeValuesRepository itemAttributeValuesRepository;

    private final FoundItemsRepository foundItemsRepository;

    private final LostItemsRepository lostItemsRepository;

    public SearchServiceHandlerImpl(ItemAttributeValuesRepository itemAttributeValuesRepository,
                                    FoundItemsRepository foundItemsRepository,
                                    LostItemsRepository lostItemsRepository) {
        this.itemAttributeValuesRepository = itemAttributeValuesRepository;
        this.foundItemsRepository = foundItemsRepository;
        this.lostItemsRepository = lostItemsRepository;
    }

    @Override
    public Collection<FoundItem> searchFoundItems(String attributeValue) {
        val itemAttributes = itemAttributeValuesRepository.search(attributeValue);
        log.info("---> Item attributes with size : {} and values : {}", itemAttributes.size(), itemAttributes);

        return foundItemsRepository.findAllByItemIdIn(itemAttributes.stream().map(ItemAttributeValue::getItemId).collect(toSet()));
    }

    @Override
    public Collection<LostItem> searchLostItems(String attributeValue) {
        val itemAttributes = itemAttributeValuesRepository.search(attributeValue);
        log.info("---> Item attributes with size : {} and values : {}", itemAttributes.size(), itemAttributes);

        return lostItemsRepository.findAllByItemIdIn(itemAttributes.stream().map(ItemAttributeValue::getItemId).collect(toSet()));

    }
}
