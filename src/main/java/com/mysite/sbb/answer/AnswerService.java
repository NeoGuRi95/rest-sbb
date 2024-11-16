package com.mysite.sbb.answer;

import com.mysite.sbb.answer.dto.request.AnswerCreateRequestDto;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final QuestionService questionService;
    private final AnswerRepository answerRepository;

    public Answer create(Integer questionId, AnswerCreateRequestDto requestDto) {
        Question question = questionService.get(questionId);
        Answer answer = new Answer();
        answer.setContent(requestDto.getContent());
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        return this.answerRepository.save(answer);
    }
}
