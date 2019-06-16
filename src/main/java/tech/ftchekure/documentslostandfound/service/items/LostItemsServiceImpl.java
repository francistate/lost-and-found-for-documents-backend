package tech.ftchekure.documentslostandfound.service.items;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.items.ItemAttributesRepository;
import tech.ftchekure.documentslostandfound.daos.items.ItemTypesRepository;
import tech.ftchekure.documentslostandfound.daos.items.ItemsRepository;
import tech.ftchekure.documentslostandfound.daos.items.LostItemsRepository;
import tech.ftchekure.documentslostandfound.entities.items.Item;
import tech.ftchekure.documentslostandfound.entities.items.ItemAttribute;
import tech.ftchekure.documentslostandfound.entities.items.ItemType;
import tech.ftchekure.documentslostandfound.entities.items.LostItem;
import tech.ftchekure.documentslostandfound.service.dtos.LostItemDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class LostItemsServiceImpl implements LostItemsService {

    private final LostItemsRepository lostItemsRepository;

    private final ItemsRepository itemsRepository;

    private final ItemTypesRepository itemTypesRepository;

    private final ItemAttributesRepository itemAttributesRepository;

    public LostItemsServiceImpl(LostItemsRepository lostItemsRepository,
                                ItemsRepository itemsRepository,
                                ItemTypesRepository itemTypesRepository,
                                ItemAttributesRepository itemAttributesRepository) {
        this.lostItemsRepository = lostItemsRepository;
        this.itemsRepository = itemsRepository;
        this.itemTypesRepository = itemTypesRepository;
        this.itemAttributesRepository = itemAttributesRepository;
    }

    @Override
    public Page<LostItem> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<LostItem>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return lostItemsRepository.findAll(pageRequest);
        else
            return lostItemsRepository.findAll(spec, pageRequest);
    }

    @Override
    public List<LostItem> findAll() {
        return null;
    }

    @Override
    public LostItem save(LostItemDto lostItemDto) {

        val attributeValues = getAttributesAndValues(lostItemDto.getAttributeValues());

        val itemType = getItemType(lostItemDto.getItemTypeId());

        val description = lostItemDto.getDescription();

        val item = Item.createItem(attributeValues, itemType, description);

        val persistedItem = itemsRepository.save(item);

        val lostItem = new LostItem();
        lostItem.setItem(persistedItem);
        lostItem.setEmail(lostItemDto.getEmail());
        //lostItem.setDescription(lostItemDto.getDescription());

        return lostItemsRepository.save(lostItem);
    }

    @Override
    public LostItem update(Long id, LostItemDto lostItemDto) {
        val lostItem = findById(id);
        //lostItem.setDescription(lostItemDto.getDescription());

        val item = lostItem.getItem();
        item.setItemType(getItemType(lostItemDto.getItemTypeId()));
        item.setAttributeValues(getAttributesAndValues(lostItemDto.getAttributeValues()));
        item.setDescription(lostItemDto.getDescription());

        val updatedItem = itemsRepository.save(item);
        lostItem.setItem(updatedItem);
        return lostItemsRepository.save(lostItem);
    }

    private Map<ItemAttribute, String> getAttributesAndValues(Map<Long, String> attributeValues) {

        val attributeValues1 = new HashMap<ItemAttribute, String>();

        attributeValues.entrySet().forEach(attributeValueEntry -> {
            val attribute = itemAttributesRepository.findById(attributeValueEntry.getKey()).get();
            attributeValues1.merge(attribute, attributeValueEntry.getValue(), (v1, v2) -> v1);
        });

        return attributeValues1;

    }

    private ItemType getItemType(Long itemTypeId) {
        return itemTypesRepository.findById(itemTypeId).get();
    }

    @Override
    public void delete(Long id) {
        lostItemsRepository.deleteById(id);
    }

    @Override
    public LostItem findById(Long id) {
        return lostItemsRepository.findById(id).get();
    }
}
