package tech.ftchekure.documentslostandfound.api.items;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.items.LostItem;
import tech.ftchekure.documentslostandfound.service.dtos.LostItemDto;
import tech.ftchekure.documentslostandfound.service.items.LostItemsService;


@CrossOrigin
@RestController
public class LostItemsController {

    private final LostItemsService lostItemsService;

    public LostItemsController(LostItemsService lostItemsService) {
        this.lostItemsService = lostItemsService;
    }

    @CrossOrigin
    @GetMapping("/lost-items")
    public Page<LostItem> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
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
        return lostItemsService.findAll(listRequest);
    }

    @GetMapping("/lost-items/{id}")
    public LostItem getItemById(@PathVariable("id") Long id) {
        return lostItemsService.findById(id);
    }

    @PutMapping("/pm/lost-items/update/{id}")
    public LostItem updateItem(@PathVariable("id") Long id, @RequestBody LostItemDto lostItemDto) {
        return lostItemsService.update(id, lostItemDto);
    }

    @DeleteMapping("/lost-items/delete/{id}")
    public void deleteItem(@PathVariable("id") Long id) {
        lostItemsService.delete(id);
    }

    @PostMapping("/pm/lost-items/new")
    public LostItem saveNewItem(@RequestBody LostItemDto lostItemDto) {
        return lostItemsService.save(lostItemDto);
    }

}
