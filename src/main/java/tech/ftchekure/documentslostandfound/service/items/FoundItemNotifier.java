package tech.ftchekure.documentslostandfound.service.items;

import org.springframework.scheduling.annotation.Async;
import tech.ftchekure.documentslostandfound.entities.items.FoundItem;
import tech.ftchekure.documentslostandfound.entities.items.LostItem;

import java.util.Collection;

public interface FoundItemNotifier {

    @Async
    void notifyFoundItem(Collection<LostItem> lostItems, FoundItem foundItem);

}
