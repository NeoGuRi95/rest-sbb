package com.mysite.sbb.answer;

import com.mysite.sbb.answer.dto.request.AnswerCreateRequestDto;
import com.mysite.sbb.answer.dto.request.AnswerModifyRequestDto;
import com.mysite.sbb.answer.dto.response.AnswerResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/answer")
@RequiredArgsConstructor
@RestController
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create/{questionId}")
    public ResponseEntity<AnswerResponseDto> createAnswer(
        @PathVariable("questionId") Integer questionId,
        @Valid @RequestBody AnswerCreateRequestDto requestDto, BindingResult bindingResult)
        throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.toString());
        }
        AnswerResponseDto responseDto = this.answerService.create(questionId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<AnswerResponseDto> modifyAnswer(@PathVariable("id") Integer id,
        @Valid @RequestBody
        AnswerModifyRequestDto requestDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.toString());
        }
        AnswerResponseDto responseDto = this.answerService.modify(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
