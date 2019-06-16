package tech.ftchekure.documentslostandfound.service.user;

import tech.ftchekure.documentslostandfound.entities.user.User;
import tech.ftchekure.documentslostandfound.service.BaseEntityService;
import tech.ftchekure.documentslostandfound.service.dtos.ChangePasswordDto;
import tech.ftchekure.documentslostandfound.service.dtos.UserDto;

import java.util.Collection;

public interface UserService extends BaseEntityService<User, UserDto, Long> {

    User changePassword(String username, ChangePasswordDto changePasswordDto);

    User changeActivationStatus(Long id, boolean active);

    Collection<User> findByGroupName(String groupName);
}
