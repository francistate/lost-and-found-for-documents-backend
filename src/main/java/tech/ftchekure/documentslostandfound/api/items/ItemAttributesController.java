package tech.ftchekure.documentslostandfound.api.items;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.items.ItemAttribute;
import tech.ftchekure.documentslostandfound.service.dtos.ItemAttributeDto;
import tech.ftchekure.documentslostandfound.service.items.ItemAttributesService;

import java.util.List;

@CrossOrigin
@RestController
public class ItemAttributesController {

    private final ItemAttributesService itemAttributesService;

    public ItemAttributesController(ItemAttributesService itemAttributesService) {
        this.itemAttributesService = itemAttributesService;
    }

    @GetMapping("/item-attributes")
    public Page<ItemAttribute> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                      @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
                                      @RequestParam(required = false, defaultValue = "DESC", name = "order") String order,
                                      @RequestParam(required = false, defaultValue = "dateCreated", name = "sort") String sort,
                                      @RequestParam(required = false, name = "search") String search) {
        val listRequest = new ListRequestTemplateBuilder()
                .setDirection(order)
                .setPage(page)
                .setSize(size)
                .setSearch(search)
                .setSort(sort)
                .createListRequestTemplate();
        return itemAttributesService.findAll(listRequest);
    }

    @GetMapping("/pm/v2/item-attributes")
    public List<ItemAttribute> getItemAttributes() {
        return itemAttributesService.findAll();
    }

    @GetMapping("/item-attributes/{id}")
    public ItemAttribute getAttributeById(@PathVariable("id") Long id) {
        return itemAttributesService.findById(id);
    }

    @DeleteMapping("/item-attributes/delete/{id}")
    public void deleteAttribute(@PathVariable("id") Long id) {
        itemAttributesService.delete(id);
    }

    @PostMapping("/item-attributes/new")
    public ItemAttribute saveNewAttribute(@RequestBody ItemAttributeDto itemAttributeDto) {
        return itemAttributesService.save(itemAttributeDto);
    }

    @PutMapping("/item-attributes/update/{id}")
    public ItemAttribute updateAttribute(@PathVariable("id") Long id, @RequestBody ItemAttributeDto itemAttributeDto) {
        return itemAttributesService.update(id, itemAttributeDto);
    }


}
