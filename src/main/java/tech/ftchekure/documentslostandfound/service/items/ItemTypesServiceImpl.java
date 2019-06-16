package tech.ftchekure.documentslostandfound.service.items;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.items.ItemAttributesRepository;
import tech.ftchekure.documentslostandfound.daos.items.ItemTypesRepository;
import tech.ftchekure.documentslostandfound.entities.items.ItemAttribute;
import tech.ftchekure.documentslostandfound.entities.items.ItemType;
import tech.ftchekure.documentslostandfound.service.dtos.ItemTypeDto;

import java.util.HashSet;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ItemTypesServiceImpl implements ItemTypesService {

    private final ItemTypesRepository itemTypesRepository;

    private final ItemAttributesRepository itemAttributesRepository;

    public ItemTypesServiceImpl(ItemTypesRepository itemTypesRepository, ItemAttributesRepository itemAttributesRepository) {
        this.itemTypesRepository = itemTypesRepository;
        this.itemAttributesRepository = itemAttributesRepository;
    }

    @Override
    public Page<ItemType> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<ItemType>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return itemTypesRepository.findAll(pageRequest);
        else
            return itemTypesRepository.findAll(spec, pageRequest);
    }

    @Override
    public List<ItemType> findAll() {
        return itemTypesRepository.findAll();
    }

    @Override
    public ItemType save(ItemTypeDto itemTypeDto) {

        itemTypesRepository.findByName(itemTypeDto.getName()).ifPresent(itemType -> {
            throw new IllegalArgumentException("Item type with the same name already exist");
        });

        val itemType = new ItemType();
        val itemAttributes = new HashSet<ItemAttribute>();
        itemType.setName(itemTypeDto.getName());
        itemTypeDto.getItemAttributes().forEach(id -> {
            val optionalItemAttribute = itemAttributesRepository.findById(id);
            optionalItemAttribute.ifPresent(itemAttributes::add);
        });
        itemType.setItemAttributes(itemAttributes);
        return itemTypesRepository.save(itemType);
    }

    @Override
    public ItemType update(Long id, ItemTypeDto itemTypeDto) {
        val itemType = findById(id);
        itemType.setName(itemTypeDto.getName());
        return itemTypesRepository.save(itemType);
    }


    @Override
    public void delete(Long id) {
        itemTypesRepository.deleteById(id);
    }

    @Override
    public ItemType findById(Long id) {
        return itemTypesRepository.findById(id).get();
    }
}
