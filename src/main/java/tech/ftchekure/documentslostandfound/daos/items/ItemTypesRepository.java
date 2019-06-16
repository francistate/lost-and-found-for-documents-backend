package tech.ftchekure.documentslostandfound.daos.items;

import io.github.hobbstech.springdatajpahelper.repository.BaseRepository;
import tech.ftchekure.documentslostandfound.entities.items.ItemType;

import java.util.Optional;

public interface ItemTypesRepository extends BaseRepository<ItemType> {

    Optional<ItemType> findByName(String name);

}
