package tech.ftchekure.documentslostandfound.api.items;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.items.ItemType;
import tech.ftchekure.documentslostandfound.service.dtos.ItemTypeDto;
import tech.ftchekure.documentslostandfound.service.items.ItemTypesService;

import java.util.List;

@CrossOrigin
@RestController
public class ItemTypesController {

    private final ItemTypesService itemTypesService;

    public ItemTypesController(ItemTypesService itemTypesService) {
        this.itemTypesService = itemTypesService;
    }

    @GetMapping("/item-types")
    public Page<ItemType> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
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
        return itemTypesService.findAll(listRequest);
    }

    @GetMapping("/pm/v2/item-types")
    public List<ItemType> getItemTypes() {
        return itemTypesService.findAll();
    }

    @GetMapping("/item-types/{id}")
    public ItemType getItemTypeById(@PathVariable("id") Long id) {
        return itemTypesService.findById(id);
    }

    @DeleteMapping("/item-types/delete/{id}")
    public void deleteItemType(@PathVariable("id") Long id) {
        itemTypesService.delete(id);
    }

    @PostMapping("/item-types/new")
    public ItemType saveItemType(@RequestBody ItemTypeDto itemTypeDto) {
        return itemTypesService.save(itemTypeDto);
    }

    @PutMapping("/item-types/update/{id}")
    public ItemType updateItemType(@PathVariable("id") Long id, @RequestBody ItemTypeDto itemTypeDto) {
        return itemTypesService.update(id, itemTypeDto);
    }

}
