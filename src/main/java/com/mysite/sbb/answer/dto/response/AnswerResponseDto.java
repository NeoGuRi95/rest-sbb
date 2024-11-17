package com.mysite.sbb.answer.dto.response;

import com.mysite.sbb.answer.Answer;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponseDto {

    private Integer id;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public AnswerResponseDto(Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
        this.createDate = answer.getCreateDate();
        this.modifyDate = answer.getModifyDate();
    }
}
