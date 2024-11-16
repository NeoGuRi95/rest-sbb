package com.mysite.sbb.question;

import com.mysite.sbb.question.dto.request.QuestionCreateRequestDto;
import com.mysite.sbb.question.dto.request.QuestionModifyRequestDto;
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

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<Page<Question>> getQuestionList(
        @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> questionPage = this.questionService.getList(page);
        return ResponseEntity.ok(questionPage);
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public ResponseEntity<Question> getQuestion(@PathVariable("id") Integer id) {
        Question question = this.questionService.get(id);
        return ResponseEntity.ok(question);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Question> createQuestion(
        @Valid @RequestBody QuestionCreateRequestDto requestDto, BindingResult bindingResult)
        throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.toString());
        }
        Question question = this.questionService.create(requestDto);
        return ResponseEntity.ok(question);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Question> modifyQuestion(@PathVariable("id") Integer id,
        @Valid @RequestBody QuestionModifyRequestDto requestDto, BindingResult bindingResult)
        throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.toString());
        }
        Question question = this.questionService.modify(id, requestDto);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable("id") Integer id) {
        this.questionService.delete(id);
    }
}
