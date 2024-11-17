package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.common.exception.DataNotFoundException;
import com.mysite.sbb.question.dto.request.QuestionCreateRequestDto;
import com.mysite.sbb.question.dto.request.QuestionModifyRequestDto;
import com.mysite.sbb.question.dto.response.QuestionResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                    cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                    cb.like(a.get("content"), "%" + kw + "%"));     // 답변 내용
            }
        };
    }

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

    public Page<QuestionResponseDto> getResponseDtoPage(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        /*Specification<Question> spec = search(kw);
        Page<Question> questionPage = questionRepository.findAll(spec, pageable);*/
        Page<Question> questionPage = questionRepository.findAllByKeyword(kw, pageable);
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
