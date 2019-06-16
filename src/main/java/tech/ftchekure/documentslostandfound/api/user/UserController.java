package tech.ftchekure.documentslostandfound.api.user;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.user.User;
import tech.ftchekure.documentslostandfound.service.dtos.UserDto;
import tech.ftchekure.documentslostandfound.service.dtos.ChangePasswordDto;
import tech.ftchekure.documentslostandfound.service.user.UserService;

import java.security.Principal;
import java.util.Collection;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Page<User> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                             @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
                             @RequestParam(required = false, defaultValue = "DESC", name = "order") String order,
                             @RequestParam(required = false, defaultValue = "dateCreated", name = "sort") String sort,
                             @RequestParam(required = false, name = "search") String search) {
        val listRequest = new ListRequestTemplateBuilder()
                .setDirection(order)
                .setPage(page)
                .setSize(size)
                .setSearch(search)
                .setSort(sort)
                .createListRequestTemplate();
        return userService.findAll(listRequest);

    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PostMapping("/users/new")
    public User saveNewUser(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/users/update/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        return userService.update(id, userDto);
    }

    @DeleteMapping("/users/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PostMapping("/users/change-password")
    public User changePassword(@RequestBody ChangePasswordDto changePasswordDto, Principal principal) {
        return userService.changePassword(principal.getName(), changePasswordDto);
    }

    @GetMapping("/users/group/search")
    public Collection<User> searchByGroup(@RequestParam("groupName") String groupName) {

        return userService.findByGroupName(groupName);
    }

    @PutMapping("/users/status/{id}")
    public User changeActivationStatus(@RequestParam ("active") boolean active,
                                       @PathVariable ("id") Long id){
        return userService.changeActivationStatus(id, active);
    }
}
