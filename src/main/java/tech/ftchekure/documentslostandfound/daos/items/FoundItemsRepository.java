package tech.ftchekure.documentslostandfound.daos.items;

import io.github.hobbstech.springdatajpahelper.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.ftchekure.documentslostandfound.entities.items.FoundItem;

import java.util.Collection;

public interface FoundItemsRepository extends BaseRepository<FoundItem> {

    @Query(value = "select fi from FoundItem fi where fi.item.id in :itemIds")
    Collection<FoundItem> findAllByItemIdIn(@Param("itemIds") Collection<Long> itemIds);

    Collection<FoundItem> findAllByStationId(Long stationId);


}
