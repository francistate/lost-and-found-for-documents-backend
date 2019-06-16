package tech.ftchekure.documentslostandfound.api.search;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.entities.items.LostItem;
import tech.ftchekure.documentslostandfound.service.search.SearchServiceHandler;

import java.util.Collection;

@CrossOrigin
@RestController
public class SearchController {

    private final SearchServiceHandler searchServiceHandler;

    public SearchController(SearchServiceHandler searchServiceHandler) {
        this.searchServiceHandler = searchServiceHandler;
    }

    @GetMapping("/pm/search-found-item")
    public Collection<FoundItem> searchFoundItem(@RequestParam("value") String attributeValue) {

        return searchServiceHandler.searchFoundItems(attributeValue);
    }

    @GetMapping("/search-lost-item")
    public Collection<LostItem> searchLostItem(@RequestParam("value") String attributeValue) {

        return searchServiceHandler.searchLostItems(attributeValue);
    }
}
