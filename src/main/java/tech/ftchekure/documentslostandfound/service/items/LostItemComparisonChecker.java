package tech.ftchekure.documentslostandfound.service.items;

import tech.ftchekure.documentslostandfound.entities.items.FoundItem;

public interface LostItemComparisonChecker {

    void compare(FoundItem foundItem);

}
