package tech.ftchekure.documentslostandfound.daos.user;

import io.github.hobbstech.springdatajpahelper.repository.BaseRepository;
import tech.ftchekure.documentslostandfound.entities.user.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUsername(String username);

    Collection<User> findAllByGroup_Name(String groupName);
}
