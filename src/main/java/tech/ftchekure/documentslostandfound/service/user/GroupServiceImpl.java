package tech.ftchekure.documentslostandfound.service.user;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.user.GroupRepository;
import tech.ftchekure.documentslostandfound.entities.user.Group;
import tech.ftchekure.documentslostandfound.service.dtos.GroupDto;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Page<Group> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<Group>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return groupRepository.findAll(pageRequest);
        else
            return groupRepository.findAll(spec, pageRequest);

    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group save(GroupDto groupDto) {
        val group = new Group();
        group.setName(groupDto.getName());
        return groupRepository.save(group);
    }

    @Override
    public Group update(Long id, GroupDto groupDto) {
        val group = findById(id);
        group.setName(groupDto.getName());
        return groupRepository.save(group);
    }

    @Override
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.findById(id).get();
    }
}
