package tech.ftchekure.documentslostandfound.service.user;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.user.AgentApplicationRepository;
import tech.ftchekure.documentslostandfound.entities.user.AgentApplication;
import tech.ftchekure.documentslostandfound.service.dtos.AgentApplicationDto;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class AgentApplicationServiceImpl implements AgentApplicationService {

    private final AgentApplicationRepository agentApplicationRepository;

    public AgentApplicationServiceImpl(AgentApplicationRepository agentApplicationRepository) {
        this.agentApplicationRepository = agentApplicationRepository;
    }

    @Override
    public Page<AgentApplication> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<AgentApplication>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return agentApplicationRepository.findAll(pageRequest);
        else
            return agentApplicationRepository.findAll(spec, pageRequest);
    }

    @Override
    public List<AgentApplication> findAll() {
        return agentApplicationRepository.findAll();
    }

    @Override
    public AgentApplication save(AgentApplicationDto agentApplicationDto) {
        val agentApplication = new AgentApplication();
        agentApplication.setAddress(agentApplicationDto.getAddress());
        agentApplication.setEmail(agentApplicationDto.getEmail());
        agentApplication.setUsername(agentApplicationDto.getUsername());
        agentApplication.setFirstName(agentApplicationDto.getFirstName());
        agentApplication.setLastName(agentApplicationDto.getLastName());
        agentApplication.setPassword(agentApplicationDto.getPassword());
        agentApplication.setPhoneNumber(agentApplicationDto.getPhoneNumber());
        agentApplication.setStationName(agentApplicationDto.getStationName());
        agentApplication.setStationLocation(agentApplicationDto.getStationLocation());
        agentApplication.setStationAddress(agentApplicationDto.getStationAddress());
        return agentApplicationRepository.save(agentApplication);

    }

    @Override
    public AgentApplication update(Long id, AgentApplicationDto agentApplicationDto) {
       val agentApplication = findById(id);
       agentApplication.setAddress(agentApplicationDto.getAddress());
       agentApplication.setUsername(agentApplicationDto.getUsername());
       agentApplication.setFirstName(agentApplicationDto.getFirstName());
       agentApplication.setLastName(agentApplicationDto.getLastName());
       agentApplication.setEmail(agentApplicationDto.getEmail());
       agentApplication.setPassword(agentApplicationDto.getPassword());
       agentApplication.setPhoneNumber(agentApplicationDto.getPhoneNumber());
       agentApplication.setStationName(agentApplicationDto.getStationName());
       agentApplication.setStationLocation(agentApplicationDto.getStationLocation());
       agentApplication.setStationAddress(agentApplicationDto.getStationAddress());
       return agentApplicationRepository.save(agentApplication);

    }

    @Override
    public void delete(Long id) {
        agentApplicationRepository.deleteById(id);
    }

    @Override
    public AgentApplication findById(Long id) {
        return agentApplicationRepository.findById(id).get();
    }
}
