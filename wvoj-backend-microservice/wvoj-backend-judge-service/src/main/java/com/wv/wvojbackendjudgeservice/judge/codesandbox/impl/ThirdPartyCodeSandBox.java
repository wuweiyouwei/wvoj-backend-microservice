package com.wv.wvojbackendjudgeservice.judge.codesandbox.impl;


import com.wv.wvojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的沙箱）
 * @author wv
 * @version V1.0
 * @date 2023/12/6 16:32
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeRequest) {
        System.out.println("第三方代码沙箱");

        return null;
    }
}
