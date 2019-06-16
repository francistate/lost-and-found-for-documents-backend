package tech.ftchekure.documentslostandfound.service.items;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.service.search.SearchServiceHandler;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class LostItemComparisonCheckerImpl implements LostItemComparisonChecker {

    private final SearchServiceHandler searchServiceHandler;

    private final FoundItemNotifier foundItemNotifier;

    public LostItemComparisonCheckerImpl(SearchServiceHandler searchServiceHandler,
                                         FoundItemNotifier foundItemNotifier) {
        this.searchServiceHandler = searchServiceHandler;
        this.foundItemNotifier = foundItemNotifier;
    }

    @Override
    public void compare(FoundItem foundItem) {

        val attributeValues = foundItem.getItem().getAttributeValues();

        val lostItems = searchServiceHandler.searchLostItems(attributeValues.values().stream().findAny().get());

        val matchingItems = lostItems.stream().filter(lostItem -> lostItem.getItem().getAttributeValues().values()
                .containsAll(foundItem.getItem().getAttributeValues().values()))
                .collect(toList());

        log.info("---> Matching Items : {}", matchingItems);

        foundItemNotifier.notifyFoundItem(matchingItems, foundItem);

    }
}
