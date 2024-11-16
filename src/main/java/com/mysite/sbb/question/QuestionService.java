package com.mysite.sbb.question;

import com.mysite.sbb.common.exception.DataNotFoundException;
import com.mysite.sbb.question.dto.request.QuestionCreateRequestDto;
import com.mysite.sbb.question.dto.request.QuestionModifyRequestDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    public Question get(Integer id) {
        Optional<Question> opQuestion = questionRepository.findById(id);
        if (opQuestion.isPresent()) {
            return opQuestion.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public Question create(QuestionCreateRequestDto requestDto) {
        Question q = new Question();
        q.setSubject(requestDto.getSubject());
        q.setContent(requestDto.getContent());
        q.setCreateDate(LocalDateTime.now());
        return this.questionRepository.save(q);
    }

    public Question modify(Integer id, QuestionModifyRequestDto requestDto) {
        Question question = this.get(id);
        question.setSubject(requestDto.getSubject());
        question.setContent(requestDto.getContent());
        question.setModifyDate(LocalDateTime.now());
        return this.questionRepository.save(question);
    }

    public void delete(Integer id) {
        Question question = this.get(id);
        this.questionRepository.delete(question);
    }
}
