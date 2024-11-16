package com.mysite.sbb.answer;

import com.mysite.sbb.answer.dto.request.AnswerCreateRequestDto;
import com.mysite.sbb.answer.dto.request.AnswerModifyRequestDto;
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

    public Answer get(Integer id) {
        Optional<Answer> opAnswer = this.answerRepository.findById(id);
        if (opAnswer.isPresent()) {
            return opAnswer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public Answer create(Integer questionId, AnswerCreateRequestDto requestDto) {
        Question question = questionService.get(questionId);
        Answer answer = new Answer();
        answer.setContent(requestDto.getContent());
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        return this.answerRepository.save(answer);
    }

    public Answer modify(Integer id, AnswerModifyRequestDto requestDto) {
        Answer answer = this.get(id);
        answer.setContent(requestDto.getContent());
        answer.setModifyDate(LocalDateTime.now());
        return this.answerRepository.save(answer);
    }

    public void delete(Integer id) {
        Answer answer = this.get(id);
        this.answerRepository.delete(answer);
    }
}
