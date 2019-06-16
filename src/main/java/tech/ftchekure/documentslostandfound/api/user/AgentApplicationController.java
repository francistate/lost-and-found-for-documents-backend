package tech.ftchekure.documentslostandfound.api.user;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.user.AgentApplication;
import tech.ftchekure.documentslostandfound.service.dtos.AgentApplicationDto;
import tech.ftchekure.documentslostandfound.service.user.AgentApplicationService;

@CrossOrigin
@RestController
public class AgentApplicationController {

    private final AgentApplicationService agentApplicationService;

    public AgentApplicationController(AgentApplicationService agentApplicationService) {
        this.agentApplicationService = agentApplicationService;
    }

    @GetMapping("/agent-applications/all")
    public Page<AgentApplication> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
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
        return agentApplicationService.findAll(listRequest);
    }

    @GetMapping("agent-applications/{id}")
    public AgentApplication getAgentApplicationById(@PathVariable("id") Long id) {
        return agentApplicationService.findById(id);
    }

    @DeleteMapping("/agent-applications/delete/{id}")
    public void deleteAgentApplication(@PathVariable("id") Long id) {
        agentApplicationService.delete(id);
    }

    @PostMapping("/pm/agent-applications/new")
    public AgentApplication saveNewAgentApplication(@RequestBody AgentApplicationDto agentApplicationDto) {
        return agentApplicationService.save(agentApplicationDto);
    }

    @PutMapping("/pm/agent-applications/update/{id}")
    public AgentApplication updateAgentApplication(@PathVariable("id") Long id, AgentApplicationDto agentApplicationDto) {
        return agentApplicationService.update(id, agentApplicationDto);
    }

}
