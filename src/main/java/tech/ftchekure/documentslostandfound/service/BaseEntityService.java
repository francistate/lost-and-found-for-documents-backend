package tech.ftchekure.documentslostandfound.service;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseEntityService<T, DTO, ID> {

    Page<T> findAll(ListRequestTemplate listRequestTemplate);

    List<T> findAll();

    T save(DTO dto);

    T update(ID id, DTO dto);

    void delete(ID id);

    T findById(ID id);

}
