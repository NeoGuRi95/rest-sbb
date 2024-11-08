package com.mysite.sbb.question;

import com.mysite.sbb.common.exception.DataNotFoundException;
import com.mysite.sbb.question.dto.request.QuestionCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
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
}
