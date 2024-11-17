package com.mysite.sbb.question;

import com.mysite.sbb.question.dto.request.QuestionCreateRequestDto;
import com.mysite.sbb.question.dto.request.QuestionModifyRequestDto;
import com.mysite.sbb.question.dto.response.QuestionResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/question")
@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/detail/{id}")
    @ResponseBody
    public ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable("id") Integer id) {
        QuestionResponseDto responseDto = this.questionService.getResponseDtoById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<Page<QuestionResponseDto>> getQuestionList(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<QuestionResponseDto> questionPage = this.questionService.getResponseDtoPage(page, kw);
        return ResponseEntity.ok(questionPage);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<QuestionResponseDto> createQuestion(
        @Valid @RequestBody QuestionCreateRequestDto requestDto, BindingResult bindingResult)
        throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.toString());
        }
        QuestionResponseDto responseDto = this.questionService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<QuestionResponseDto> modifyQuestion(@PathVariable("id") Integer id,
        @Valid @RequestBody QuestionModifyRequestDto requestDto, BindingResult bindingResult)
        throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.toString());
        }
        QuestionResponseDto responseDto = this.questionService.modify(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable("id") Integer id) {
        this.questionService.delete(id);
    }
}
