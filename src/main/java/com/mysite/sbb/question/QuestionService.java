package com.mysite.sbb.question;

import com.mysite.sbb.common.exception.DataNotFoundException;
import com.mysite.sbb.question.dto.request.QuestionCreateRequestDto;
import com.mysite.sbb.question.dto.request.QuestionModifyRequestDto;
import com.mysite.sbb.question.dto.response.QuestionResponseDto;
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

    public Question getEntityById(Integer id) {
        Optional<Question> opQuestion = questionRepository.findById(id);
        if (opQuestion.isPresent()) {
            return opQuestion.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public QuestionResponseDto getResponseDtoById(Integer id) {
        Question question = this.getEntityById(id);
        return new QuestionResponseDto(question);
    }

    public Page<QuestionResponseDto> getResponseDtoPage(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Question> questionPage = questionRepository.findAll(pageable);
        return questionPage.map(QuestionResponseDto::new);
    }

    public QuestionResponseDto create(QuestionCreateRequestDto requestDto) {
        Question question = new Question();
        question.setSubject(requestDto.getSubject());
        question.setContent(requestDto.getContent());
        question.setCreateDate(LocalDateTime.now());
        Question savedQuestion = this.questionRepository.save(question);
        return new QuestionResponseDto(savedQuestion);
    }

    public QuestionResponseDto modify(Integer id, QuestionModifyRequestDto requestDto) {
        Question question = this.getEntityById(id);
        question.setSubject(requestDto.getSubject());
        question.setContent(requestDto.getContent());
        question.setModifyDate(LocalDateTime.now());
        Question modifiedQuestion = this.questionRepository.save(question);
        return new QuestionResponseDto(modifiedQuestion);
    }

    public void delete(Integer id) {
        Question question = this.getEntityById(id);
        this.questionRepository.delete(question);
    }
}
