package tech.ftchekure.documentslostandfound.api.user;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.user.Station;
import tech.ftchekure.documentslostandfound.service.dtos.StationDto;
import tech.ftchekure.documentslostandfound.service.user.StationService;

import java.util.List;

@CrossOrigin
@RestController
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/pm/stations")
    public Page<Station> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
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
        return stationService.findAll(listRequest);
    }

    @GetMapping("/v2/stations")
    public List<Station> getStations() {
        return stationService.findAll();
    }

    @PostMapping("/stations/new")
    public Station saveNewStation(@RequestBody StationDto stationDto) {
        return stationService.save(stationDto);
    }

    @PutMapping("/stations/update/{id}")
    public Station updateStation(@PathVariable("id") Long id, @RequestBody StationDto stationDto) {
        return stationService.update(id, stationDto);
    }

    @DeleteMapping("/stations/delete/{id}")
    public void deleteStation(@PathVariable("id") Long id) {
        stationService.delete(id);
    }

    @GetMapping("/stations/{id}")
    public Station findById(@PathVariable("id") Long id) {
        return stationService.findById(id);
    }
}
