package com.mini.rpc.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 自定义的Future类，用于存储 Promise对象 和 超时时间
 * @date 2022/6/20 3:07 下午
 */
@Data
public class MiniRpcFuture<T> {

    private Promise<T> promise;

    private long timeout;

    public MiniRpcFuture(Promise<T> promise, long timeout) {
        this.promise = promise;
        this.timeout = timeout;
    }
}
