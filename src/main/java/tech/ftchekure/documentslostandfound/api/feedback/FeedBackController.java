package tech.ftchekure.documentslostandfound.api.feedback;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplateBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tech.ftchekure.documentslostandfound.entities.feedback.FeedBack;
import tech.ftchekure.documentslostandfound.service.dtos.FeedBackDto;
import tech.ftchekure.documentslostandfound.service.feedback.FeedBackService;

@CrossOrigin
@RestController
public class FeedBackController {

    private final FeedBackService feedBackService;

    public FeedBackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @GetMapping("/feedback/all")
    public Page<FeedBack> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
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
        return feedBackService.findAll(listRequest);
    }

    @GetMapping("/feedback/{id}")
    public FeedBack getFeedBackById(@PathVariable("id") Long id) {
        return feedBackService.findById(id);
    }

    @DeleteMapping("/feedback/delete/{id}")
    public void deleteFeedback(@PathVariable("id") Long id) {
        feedBackService.delete(id);
    }

    @PostMapping("/pm/feedback/new")
    public FeedBack saveNewFeedBack(@RequestBody FeedBackDto feedBackDto) {
        return feedBackService.save(feedBackDto);
    }

    @PutMapping("/pm/feedback/update/{id}")
    public FeedBack feedBack(@PathVariable("id") Long id, FeedBackDto feedBackDto) {
        return feedBackService.update(id, feedBackDto);
    }

}
