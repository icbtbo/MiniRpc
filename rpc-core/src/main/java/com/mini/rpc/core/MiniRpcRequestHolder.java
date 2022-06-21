package com.mini.rpc.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 用于获取 requestId 以及 存储 requestId 和 MiniRpcFuture<MiniRpcResponse> 之间的映射关系
 * @date 2022/6/20 3:04 下午
 */
public class MiniRpcRequestHolder {
    /** 原子变量，用于产生 requestId */
    public static final AtomicLong REQUEST_ID_GEN = new AtomicLong(0);
    /** 用于存储 requestId 和 MiniRpcFuture<MiniRpcResponse> 之间的映射关系，以便设置响应结果*/
    public static final Map<Long, MiniRpcFuture<MiniRpcResponse>> REQUEST_MAP = new ConcurrentHashMap<>();

}
