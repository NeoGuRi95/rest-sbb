package com.mysite.sbb.question.dto.response;

import com.mysite.sbb.answer.dto.response.AnswerResponseDto;
import com.mysite.sbb.question.Question;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResponseDto {

    private Integer id;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private List<AnswerResponseDto> answerList;

    public QuestionResponseDto(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.createDate = question.getCreateDate();
        this.modifyDate = question.getModifyDate();
        this.answerList = question.getAnswerList().stream().map(AnswerResponseDto::new).toList();
    }
}
