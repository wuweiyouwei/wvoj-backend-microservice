package com.wv.wvojbackendjudgeservice.judge.codesandbox;


import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱代理（便于增强操作）
 * @author wv
 * @version V1.0
 * @date 2023/12/6 18:03
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox{


    private CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeRequest) {

        log.info("executeRequest:{}",executeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeRequest);
        return executeCodeResponse;
    }

}
