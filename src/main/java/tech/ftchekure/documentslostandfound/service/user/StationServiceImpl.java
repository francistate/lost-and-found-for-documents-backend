package tech.ftchekure.documentslostandfound.service.user;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.user.StationRepository;
import tech.ftchekure.documentslostandfound.entities.user.Station;
import tech.ftchekure.documentslostandfound.service.dtos.StationDto;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class StationServiceImpl implements StationService {

    private StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Page<Station> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<Station>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return stationRepository.findAll(pageRequest);
        else
            return stationRepository.findAll(spec, pageRequest);
    }

    @Override
    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    @Override
    public Station save(StationDto stationDto) {
        val station = new Station();
        station.setAddress(stationDto.getAddress());
        station.setName(stationDto.getName());
        station.setLocation(stationDto.getLocation());
        return stationRepository.save(station);
    }

    @Override
    public Station update(Long id, StationDto stationDto) {
        val station = findById(id);
        station.setAddress(stationDto.getAddress());
        station.setName(stationDto.getName());
        station.setLocation(stationDto.getLocation());
        return stationRepository.save(station);
    }

    @Override
    public void delete(Long id) {
        stationRepository.deleteById(id);
    }

    @Override
    public Station findById(Long id) {
        return stationRepository.findById(id).get();
    }
}
