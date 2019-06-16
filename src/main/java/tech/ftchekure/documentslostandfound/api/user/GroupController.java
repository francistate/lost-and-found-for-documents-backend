package tech.ftchekure.documentslostandfound.api.user;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.user.Group;
import tech.ftchekure.documentslostandfound.service.dtos.GroupDto;
import tech.ftchekure.documentslostandfound.service.user.GroupService;

import java.util.List;


@CrossOrigin
@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public Page<Group> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
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
        return groupService.findAll(listRequest);
    }

    @GetMapping("/v2/groups")
    public List<Group> getGroups() {
        return groupService.findAll();
    }

    @GetMapping("/groups/{id}")
    public Group getGroupById(@PathVariable("id") Long id) {
        return groupService.findById(id);
    }

    @DeleteMapping("/groups/delete/{id}")
    public void deleteGroup(@PathVariable("id") Long id) {
        groupService.delete(id);
    }

    @PostMapping("/groups/new")
    public Group saveGroup(@RequestBody GroupDto groupDto) {
        return groupService.save(groupDto);
    }

    @PutMapping("/groups/update/{id}")
    public Group updateGroup(@PathVariable("id") Long id, @RequestBody GroupDto groupDto) {
        return groupService.update(id, groupDto);
    }


}
