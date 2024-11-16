package com.mysite.sbb.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/answer")
@RequiredArgsConstructor
@RestController
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create/{questionId}")
    public ResponseEntity<Answer> createAnswer(@PathVariable("questionId") Integer questionId, @RequestParam(value = "content") String content) {
        Answer answer = this.answerService.create(questionId, content);
        return ResponseEntity.ok(answer);
    }
}
