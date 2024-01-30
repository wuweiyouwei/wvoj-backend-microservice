package com.wv.wvojbackendserviceclient.service;


import com.wv.wvojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 判题服务
 * @author wv
 * @version V1.0
 * @date 2023/12/6 18:18
 */
@FeignClient(name = "wvoj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    /**
     * 判题服务
     * @param questionSubmitId 题目 id
     * @return
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
