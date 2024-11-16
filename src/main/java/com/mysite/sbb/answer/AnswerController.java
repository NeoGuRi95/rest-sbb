package com.mysite.sbb.answer;

import com.mysite.sbb.answer.dto.request.AnswerCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/answer")
@RequiredArgsConstructor
@RestController
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create/{questionId}")
    public ResponseEntity<Answer> createAnswer(@PathVariable("questionId") Integer questionId,
        @Valid @RequestBody AnswerCreateRequestDto requestDto, BindingResult bindingResult)
        throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.toString());
        }
        Answer answer = this.answerService.create(questionId, requestDto);
        return ResponseEntity.ok(answer);
    }
}
