package com.wv.wvojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wv.wvojbackendcommon.common.ErrorCode;
import com.wv.wvojbackendcommon.exception.BusinessException;
import com.wv.wvojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.wv.wvojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 * @author wv
 * @version V1.0
 * @date 2023/12/6 16:32
 */
public class RemoteCodeSandBox implements CodeSandBox {

    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeRequest) {
        System.out.println("远程代码沙箱");

        // API 调用远程代码沙箱
        String url = "http://localhost:8090/executeCode";
        String requestJson = JSONUtil.toJsonStr(executeRequest);
        String responseStr = HttpUtil.createPost(url)
                .body(requestJson)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .execute()
                .body();
        if (StrUtil.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"executeCode remoteCodeSandBox error, message = " + responseStr);
        }
        ExecuteCodeResponse executeCodeResponse = JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
        System.out.println(executeCodeResponse);
        return executeCodeResponse;
    }
}
