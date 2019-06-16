package tech.ftchekure.documentslostandfound.service.items;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.items.ItemAttributesRepository;
import tech.ftchekure.documentslostandfound.entities.items.ItemAttribute;
import tech.ftchekure.documentslostandfound.service.dtos.ItemAttributeDto;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ItemAttributesServiceImpl implements ItemAttributesService {

    private final ItemAttributesRepository itemAttributesRepository;

    public ItemAttributesServiceImpl(ItemAttributesRepository itemAttributesRepository) {
        this.itemAttributesRepository = itemAttributesRepository;
    }

    @Override
    public Page<ItemAttribute> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<ItemAttribute>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return itemAttributesRepository.findAll(pageRequest);
        else
            return itemAttributesRepository.findAll(spec, pageRequest);

    }

    @Override
    public List<ItemAttribute> findAll() {
        return itemAttributesRepository.findAll();
    }

    @Override
    public ItemAttribute save(ItemAttributeDto itemAttributeDto) {
        val itemAttribute = new ItemAttribute();
        itemAttribute.setName(itemAttributeDto.getName());
        return itemAttributesRepository.save(itemAttribute);
    }

    @Override
    public ItemAttribute update(Long id, ItemAttributeDto itemAttributeDto) {
        val itemAttribute = findById(id);
        itemAttribute.setName(itemAttributeDto.getName());
        return itemAttributesRepository.save(itemAttribute);
    }

    @Override
    public void delete(Long id) {
        itemAttributesRepository.deleteById(id);
    }

    @Override
    public ItemAttribute findById(Long id) {
        return itemAttributesRepository.findById(id).get();
    }
}
