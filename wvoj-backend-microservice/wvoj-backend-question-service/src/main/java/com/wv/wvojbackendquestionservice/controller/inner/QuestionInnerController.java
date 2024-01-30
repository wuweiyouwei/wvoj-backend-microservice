package com.wv.wvojbackendquestionservice.controller.inner;

import com.wv.wvojbackendmodel.entity.Question;
import com.wv.wvojbackendmodel.entity.QuestionSubmit;
import com.wv.wvojbackendquestionservice.service.QuestionService;
import com.wv.wvojbackendquestionservice.service.QuestionSubmitService;
import com.wv.wvojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wv
 * @version V1.0
 * @date 2024/1/28 10:13
 */
@RestController()
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId){
        return questionService.getById(questionId);
    }

    @GetMapping("/question_Submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId){
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit){
        return questionSubmitService.updateById(questionSubmit);
    }
}
