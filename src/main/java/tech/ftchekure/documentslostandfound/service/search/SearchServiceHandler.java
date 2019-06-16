package tech.ftchekure.documentslostandfound.service.search;

import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.entities.items.LostItem;

import java.util.Collection;

public interface SearchServiceHandler {

    Collection<FoundItem> searchFoundItems(String attributeValue);

    Collection<LostItem> searchLostItems(String attributeValue);

}
