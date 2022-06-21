package com.mini.rpc.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc响应 协议体
 * @date 2022/6/19 2:26 下午
 */
@Data
public class MiniRpcResponse implements Serializable {
    /** 请求的响应结果 */
    private Object data;
    /** 错误信息 */
    private String message;

}
