package com.mysite.sbb.question;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void testSave() {
        // given
        List<Question> beforeQuestionList = this.questionRepository.findAll();
        Question q1 = new Question();
        q1.setSubject("test question subject1");
        q1.setContent("test question content1");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("test question subject2");
        q2.setContent("test question content2");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);

        // when
        List<Question> afterQuestionList = this.questionRepository.findAll();

        // then
        assertEquals(2, afterQuestionList.size() - beforeQuestionList.size());
        assertEquals("test question subject1", afterQuestionList.get(0).getSubject());
        assertEquals("test question content1", afterQuestionList.get(0).getContent());
    }

    @Test
    void testFind() {
        // given
        Question q1 = new Question();
        q1.setSubject("test question subject1");
        q1.setContent("test question content1");
        q1.setCreateDate(LocalDateTime.now());
        Question savedQuestion = this.questionRepository.save(q1);

        // when
        Optional<Question> findQuestion = questionRepository.findById(savedQuestion.getId());

        // then
        if (findQuestion.isPresent()) {
            assertEquals(findQuestion.get().getSubject(), savedQuestion.getSubject());
            assertEquals(findQuestion.get().getContent(), savedQuestion.getContent());
        }
    }

    @Test
    void testUpdate() {
        // given
        Question q1 = new Question();
        q1.setSubject("test question subject1");
        q1.setContent("test question content1");
        q1.setCreateDate(LocalDateTime.now());
        Question savedQuestion = this.questionRepository.save(q1);

        // when
        Optional<Question> findQuestion = this.questionRepository.findById(savedQuestion.getId());

        if (findQuestion.isPresent()) {
            findQuestion.get().setSubject("test update question subject1");
            savedQuestion = this.questionRepository.save(findQuestion.get());
            // then
            assertEquals(savedQuestion.getSubject(), "test update question subject1");
        }
    }

    @Test
    void testDelete() {
        // given
        Question q1 = new Question();
        q1.setSubject("test question subject1");
        q1.setContent("test question content1");
        q1.setCreateDate(LocalDateTime.now());
        Integer id = this.questionRepository.save(q1).getId();

        Question q2 = new Question();
        q2.setSubject("test question subject2");
        q2.setContent("test question content2");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);
        long beforeSize = this.questionRepository.count();

        // when
        Optional<Question> opQuestion = this.questionRepository.findById(id);
        if (opQuestion.isPresent()) {
            this.questionRepository.delete(opQuestion.get());
            // then
            long afterSize = this.questionRepository.count();
            assertEquals(beforeSize - 1, afterSize);
        }
    }
}
