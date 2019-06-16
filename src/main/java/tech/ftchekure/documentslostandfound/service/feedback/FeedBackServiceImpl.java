package tech.ftchekure.documentslostandfound.service.feedback;

import io.github.hobbstech.springdatajpahelper.messages.ListRequestTemplate;
import io.github.hobbstech.springdatajpahelper.specification.CustomSpecificationTemplateImplBuilder;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.ftchekure.documentslostandfound.daos.feedback.FeedBackRepository;
import tech.ftchekure.documentslostandfound.entities.feedback.FeedBack;
import tech.ftchekure.documentslostandfound.service.dtos.FeedBackDto;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    private final FeedBackRepository feedBackRepository;

    public FeedBackServiceImpl(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    @Override
    public Page<FeedBack> findAll(ListRequestTemplate listRequestTemplate) {
        val pageRequest = listRequestTemplate.getPageRequest();
        val spec = new CustomSpecificationTemplateImplBuilder<FeedBack>()
                .buildSpecification(listRequestTemplate.getSearch());
        if (isNull(listRequestTemplate.getSearch()))
            return feedBackRepository.findAll(pageRequest);
        else
            return feedBackRepository.findAll(spec, pageRequest);
    }

    @Override
    public List<FeedBack> findAll() {
        return null;
    }

    @Override
    public FeedBack save(FeedBackDto feedBackDto) {
        val feedBack = new FeedBack();
        feedBack.setMessage(feedBackDto.getMessage());
        feedBack.setEmail(feedBackDto.getEmail());
        return feedBackRepository.save(feedBack);
    }

    @Override
    public FeedBack update(Long id, FeedBackDto feedBackDto) {
        val feedBack = findById(id);
        feedBack.setEmail(feedBackDto.getEmail());
        feedBack.setMessage(feedBack.getMessage());
        return feedBackRepository.save(feedBack);

    }

    @Override
    public void delete(Long id) {
        feedBackRepository.deleteById(id);
    }

    @Override
    public FeedBack findById(Long id) {
        return feedBackRepository.findById(id).get();
    }
}
