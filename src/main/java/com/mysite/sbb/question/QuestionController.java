package com.mysite.sbb.question;

import com.mysite.sbb.question.dto.request.QuestionCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Question>> getQuestionList() {
        List<Question> questionList = this.questionService.getList();
        return ResponseEntity.ok(questionList);
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
}
