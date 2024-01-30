package com.wv.wvojbackendjudgeservice.judge.strategy;


import com.wv.wvojbackendmodel.codesandbox.JudgeInfo;
import com.wv.wvojbackendmodel.dto.question.JudgeCase;
import com.wv.wvojbackendmodel.entity.Question;
import com.wv.wvojbackendmodel.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 用于定义在策略中传递的参数
 *
 * @author wv
 * @version V1.0
 * @date 2024/1/23 8:54
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;

}
