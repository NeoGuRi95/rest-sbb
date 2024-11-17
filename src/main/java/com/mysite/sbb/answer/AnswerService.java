package com.mysite.sbb.answer;

import com.mysite.sbb.answer.dto.request.AnswerCreateRequestDto;
import com.mysite.sbb.answer.dto.request.AnswerModifyRequestDto;
import com.mysite.sbb.answer.dto.response.AnswerResponseDto;
import com.mysite.sbb.common.exception.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final QuestionService questionService;
    private final AnswerRepository answerRepository;

    public Answer getEntityById(Integer id) {
        Optional<Answer> opAnswer = this.answerRepository.findById(id);
        if (opAnswer.isPresent()) {
            return opAnswer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public AnswerResponseDto create(Integer questionId, AnswerCreateRequestDto requestDto) {
        Question question = questionService.getEntityById(questionId);
        Answer answer = new Answer();
        answer.setContent(requestDto.getContent());
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        Answer savedAnswer = this.answerRepository.save(answer);
        return new AnswerResponseDto(savedAnswer);
    }

    public AnswerResponseDto modify(Integer id, AnswerModifyRequestDto requestDto) {
        Answer answer = this.getEntityById(id);
        answer.setContent(requestDto.getContent());
        answer.setModifyDate(LocalDateTime.now());
        Answer modifiedAnswer = this.answerRepository.save(answer);
        return new AnswerResponseDto(modifiedAnswer);
    }

    public void delete(Integer id) {
        Answer answer = this.getEntityById(id);
        this.answerRepository.delete(answer);
    }
}
