package com.wv.wvojbackendmodel.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 判题配置封装类
 * @author wv
 * @version V1.0
 * @date 2023/11/25 18:08
 */
@Data
public class JudgeConfig implements Serializable {

    /**
     * 时间限制
     */
    private Long timeLimit;

    /**
     * 内存限制
     */
    private Long memoryLimit;

    /**
     * 栈限制
     */
    private Long stackLimit;

    private static final long serialVersionUID = 1L;



}
