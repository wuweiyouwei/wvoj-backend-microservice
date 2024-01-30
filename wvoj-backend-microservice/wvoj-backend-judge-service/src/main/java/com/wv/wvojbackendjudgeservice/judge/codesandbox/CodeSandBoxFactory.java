package com.wv.wvojbackendjudgeservice.judge.codesandbox;


import com.wv.wvojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandBox;
import com.wv.wvojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandBox;
import com.wv.wvojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandBox;

/**
 * 代码沙箱静态工厂
 *
 * @author wv
 * @version V1.0
 * @date 2023/12/6 16:49
 */
public class CodeSandBoxFactory {


    public static CodeSandBox getInstance(String type) {

        switch (type) {
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new ThirdPartyCodeSandBox();
            default:
                return new ExampleCodeSandBox();
        }
    }
}
