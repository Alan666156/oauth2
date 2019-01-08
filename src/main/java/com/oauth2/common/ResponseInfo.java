package com.oauth2.common;

import lombok.Data;

/**
 * 全局异常捕获实体
 * @author fuhx
 */
@Data
public class ResponseInfo {
    /**
     * HTTP状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private Object data;

    public ResponseInfo(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
