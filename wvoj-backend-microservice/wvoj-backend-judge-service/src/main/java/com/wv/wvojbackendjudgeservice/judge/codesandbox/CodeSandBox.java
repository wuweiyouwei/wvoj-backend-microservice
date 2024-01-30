package com.wv.wvojbackendjudgeservice.judge.codesandbox;


import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 * @author wv
 * @version V1.0
 * @date 2023/12/6 16:18
 */
public interface CodeSandBox {

    /**
     * 执行代码
     * @param executeRequest 请求
     * @return 通用返回结果类
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeRequest);

}
