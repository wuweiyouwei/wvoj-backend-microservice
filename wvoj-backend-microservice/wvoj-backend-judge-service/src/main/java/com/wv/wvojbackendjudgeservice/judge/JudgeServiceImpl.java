package com.wv.wvojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.wv.wvojbackendcommon.common.ErrorCode;
import com.wv.wvojbackendcommon.exception.BusinessException;
import com.wv.wvojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.wv.wvojbackendjudgeservice.judge.codesandbox.CodeSandBoxFactory;
import com.wv.wvojbackendjudgeservice.judge.codesandbox.CodeSandBoxProxy;
import com.wv.wvojbackendjudgeservice.judge.strategy.JudgeContext;
import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.wv.wvojbackendmodel.codesandbox.JudgeInfo;
import com.wv.wvojbackendmodel.dto.question.JudgeCase;
import com.wv.wvojbackendmodel.entity.Question;
import com.wv.wvojbackendmodel.entity.QuestionSubmit;
import com.wv.wvojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.wv.wvojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题服务实现类
 *
 * @author wv
 * @version V1.0
 * @date 2023/12/6 18:57
 */
@Service
public class JudgeServiceImpl implements JudgeService {


    /**
     * 代码沙箱类型
     */
    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
         /*
          业务逻辑
          1.根据提交的题目 id，查询题目的提交信息（代码，语言）
          2.如果题目的提交状态不为等待中，就不用重复执行了
          3.更改提交状态为“判题中”。防止重复执行，也能让用户立即看到状态
          4.调用沙箱，获取到执行结果
          5.根据沙箱的执行结果，设置题目的判题状态和信息
         */

        // 1.根据提交的题目 id，查询题目的提交信息（代码，语言）
        // 题目提交信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "提交信息有误");
        }
        Long questionId = questionSubmit.getQuestionId();
        // 题目信息
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }
        // 2.如果题目的提交状态为不是等待中，就不用重复执行了
        if (!QuestionSubmitStatusEnum.WAITING.getValue().equals(questionSubmit.getStatus())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目已在判题中");
        }
        // 3.更改提交状态为“判题中”。防止重复执行，也能让用户立即看到状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }

        // 4.调用沙箱，获取到执行结果
        CodeSandBox codeSandBox = CodeSandBoxFactory.getInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCases = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        // 请求封装类
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(questionSubmit.getCode())
                .language(questionSubmit.getLanguage())
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);

        // 5.根据沙箱的执行结果，设置题目的判题状态和信息
        List<String> outputList = executeCodeResponse.getOutputList();

//        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCases);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        // 更新数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }

        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionId);
        return questionSubmitResult;

    }
}
