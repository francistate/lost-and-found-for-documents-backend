package tech.ftchekure.documentslostandfound.daos.items;

import io.github.hobbstech.springdatajpahelper.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import tech.ftchekure.documentslostandfound.entities.items.LostItem;

import java.util.Collection;
import java.util.Set;

public interface LostItemsRepository extends BaseRepository<LostItem> {

    @Query(value = "select li from LostItem li where li.item.id in :itemIds")
    Collection<LostItem> findAllByItemIdIn(Set<Long> itemIds);
}
