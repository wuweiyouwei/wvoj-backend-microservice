package com.wv.wvojbackendjudgeservice.judge;

import com.wv.wvojbackendjudgeservice.judge.strategy.DefaultJudgeStrategyImpl;
import com.wv.wvojbackendjudgeservice.judge.strategy.JavaJudgeStrategyImpl;
import com.wv.wvojbackendjudgeservice.judge.strategy.JudgeContext;
import com.wv.wvojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.wv.wvojbackendmodel.codesandbox.JudgeInfo;
import com.wv.wvojbackendmodel.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 策略管理器（简化调用）
 *
 * @author wv
 * @version V1.0
 * @date 2024/1/23 9:32
 */
@Service
public class JudgeManager {

    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();

        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();

        if (language.equals("java")){
            judgeStrategy = new JavaJudgeStrategyImpl();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
