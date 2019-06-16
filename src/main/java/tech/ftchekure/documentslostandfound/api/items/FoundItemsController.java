package tech.ftchekure.documentslostandfound.api.items;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.entities.user.User;
import tech.ftchekure.documentslostandfound.service.dtos.FoundItemDto;
import tech.ftchekure.documentslostandfound.service.items.FoundItemsService;

import java.util.Collection;

@CrossOrigin
@RestController
public class FoundItemsController {

    private final FoundItemsService foundItemsService;

    public FoundItemsController(FoundItemsService foundItemsService) {
        this.foundItemsService = foundItemsService;
    }

    @GetMapping("/found-items")
    public Page<FoundItem> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
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
        return foundItemsService.findAll(listRequest);
    }

    @GetMapping("/found-items/{id}")
    public FoundItem getItemById(@PathVariable("id") Long id) {
        return foundItemsService.findById(id);
    }

    @PutMapping("/found-items/update/{id}")
    public FoundItem updateItem(@PathVariable("id") Long id, @RequestBody FoundItemDto foundItemDto) {
        return foundItemsService.update(id, foundItemDto);
    }

    @PostMapping("/found-items/new")
    public FoundItem saveNewItem(@RequestBody FoundItemDto foundItemDto) {
        return foundItemsService.save(foundItemDto);
    }

    @DeleteMapping("/found-items/delete/{id}")
    public void deleteItem(@PathVariable("id") Long id) {
        foundItemsService.delete(id);
    }

    @GetMapping("/found-items/station/search")
    public Collection<FoundItem> searchByGroup(@RequestParam("stationId") Long stationId) {

        return foundItemsService.findByStationId(stationId);

    }

}
